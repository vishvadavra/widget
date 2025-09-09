package com.epic.widgetwall.platform

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.Size
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toRect
import androidx.core.graphics.toRectF
import com.epic.widgetwall.model.PhotoWidgetAspectRatio
import com.epic.widgetwall.model.PhotoWidgetColors
import com.epic.widgetwall.model.PhotoWidgetShapeBuilder
import com.epic.widgetwall.model.rawAspectRatio
import kotlin.math.min
import timber.log.Timber

fun Bitmap.withRoundedCorners(
    radius: Float,
    aspectRatio: PhotoWidgetAspectRatio = PhotoWidgetAspectRatio.ROUNDED_SQUARE,
    colors: PhotoWidgetColors = PhotoWidgetColors(),
    @ColorInt borderColor: Int? = null,
    @FloatRange(from = 0.0) borderPercent: Float = .0F,
    widgetSize: Size? = null,
): Bitmap = withTransformation(
    aspectRatio = aspectRatio,
    colors = colors,
    borderColor = borderColor,
    borderPercent = borderPercent,
    widgetSize = widgetSize,
) { canvas, rect, paint ->
    canvas.drawRoundRect(rect.toRectF(), radius, radius, paint)
}

fun Bitmap.withPolygonalShape(
    shapeId: String,
    colors: PhotoWidgetColors = PhotoWidgetColors(),
    @ColorInt borderColor: Int? = null,
    @FloatRange(from = 0.0) borderPercent: Float = .0F,
): Bitmap = withTransformation(
    aspectRatio = PhotoWidgetAspectRatio.SQUARE,
    colors = colors,
    borderColor = borderColor,
    borderPercent = borderPercent,
    widgetSize = null,
) { canvas, rect, paint ->
    try {
        val path = PhotoWidgetShapeBuilder.getShapePath(
            shapeId = shapeId,
            width = width.toFloat(),
            height = height.toFloat(),
            rectF = rect.toRectF(),
        )

        canvas.drawPath(path, paint)
    } catch (e: Exception) {
        val message = "withPolygonalShape failed! " +
            "(shapeId=$shapeId, bitmap=$width, $height, rect=${rect.width()}, ${rect.height()})"

        throw RuntimeException(message, e)
    }
}

private inline fun Bitmap.withTransformation(
    aspectRatio: PhotoWidgetAspectRatio,
    colors: PhotoWidgetColors,
    @ColorInt borderColor: Int?,
    @FloatRange(from = 0.0) borderPercent: Float,
    widgetSize: Size?,
    body: (Canvas, Rect, Paint) -> Unit,
): Bitmap {
    val source = sourceRect(aspectRatio = aspectRatio, widgetSize = widgetSize)
    val destination = Rect(0, 0, source.width(), source.height())

    val output = createBitmap(source.width(), source.height())
    val canvas = Canvas(output).apply {
        drawColor(Color.TRANSPARENT)
    }
    val basePaint = Paint().apply {
        isAntiAlias = true
        alpha = (colors.opacity * 255 / 100).toInt()
    }

    val bodyRect = if (PhotoWidgetAspectRatio.SQUARE == aspectRatio) source else destination

    body(canvas, bodyRect, basePaint)

    val bitmapPaint = Paint(basePaint).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        val brightness = colors.brightness * 255 / 100
        val brightnessMatrix = floatArrayOf(
            1f, 0f, 0f, 0f, brightness,
            0f, 1f, 0f, 0f, brightness,
            0f, 0f, 1f, 0f, brightness,
            0f, 0f, 0f, 1f, 0f,
        )

        val colorMatrix = ColorMatrix().apply {
            setSaturation(colors.saturation / 100)
            postConcat(ColorMatrix(brightnessMatrix))
        }
        val colorFilter = ColorMatrixColorFilter(colorMatrix)

        setColorFilter(colorFilter)
    }

    canvas.drawBitmap(this, source, destination, bitmapPaint)

    if (borderColor != null && borderPercent > 0) {
        val strokePaint = Paint(basePaint).apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            style = Paint.Style.STROKE
            color = borderColor
            strokeWidth = min(output.width, output.height) * borderPercent
        }
        body(canvas, bodyRect, strokePaint)
    }

    return output
}

private fun Bitmap.sourceRect(
    aspectRatio: PhotoWidgetAspectRatio,
    widgetSize: Size? = null,
): Rect {
    Timber.d(
        "Calculating source rect for bitmap (" +
            "width=$width," +
            "height=$height," +
            "aspectRatio=$aspectRatio," +
            "widgetSize=$widgetSize" +
            ")",
    )

    return createCenteredRectWithAspectRatio(
        bitmapWidth = width.toFloat(),
        bitmapHeight = height.toFloat(),
        aspectRatio = when (aspectRatio) {
            PhotoWidgetAspectRatio.ORIGINAL -> width / height.toFloat()

            PhotoWidgetAspectRatio.FILL_WIDGET -> {
                if (widgetSize != null && widgetSize.width > 0 && widgetSize.height > 0) {
                    widgetSize.width / widgetSize.height.toFloat()
                } else {
                    width / height.toFloat()
                }
            }

            else -> aspectRatio.rawAspectRatio
        },
    ).toRect().also { Timber.d("Output rect: $it") }
}

/**
 * Creates a centered rectangle with the specified aspect ratio inside a bitmap
 *
 * @param bitmapWidth Width of the bitmap
 * @param bitmapHeight Height of the bitmap
 * @param aspectRatio Width/height ratio the created rect should maintain
 * @return A RectF centered in the bitmap with the specified aspect ratio
 */
private fun createCenteredRectWithAspectRatio(
    bitmapWidth: Float,
    bitmapHeight: Float,
    aspectRatio: Float,
): RectF {
    val bitmapAspectRatio = bitmapWidth / bitmapHeight

    val rectWidth: Float
    val rectHeight: Float

    // Determine if we should fit by width or height
    if (aspectRatio > bitmapAspectRatio) {
        // Width constrained
        rectWidth = bitmapWidth
        rectHeight = rectWidth / aspectRatio
    } else {
        // Height constrained
        rectHeight = bitmapHeight
        rectWidth = rectHeight * aspectRatio
    }

    // Calculate top-left position to center the rect
    val left = (bitmapWidth - rectWidth) / 2
    val top = (bitmapHeight - rectHeight) / 2

    return RectF(left, top, left + rectWidth, top + rectHeight)
}
