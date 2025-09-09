package com.epic.widgetwall.platform

import android.app.ActivityManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.PowerManager
import android.util.DisplayMetrics
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.epic.widgetwall.model.PhotoWidget
import com.google.android.material.color.DynamicColors
import kotlin.math.roundToInt
import kotlin.math.sqrt
import timber.log.Timber

@ColorInt
fun Context.getAttributeColor(
    @AttrRes attrId: Int,
    @ColorInt default: Int = -1,
): Int {
    val resolved = obtainStyledAttributes(intArrayOf(attrId))
    val color = resolved.getColor(0, default)
    resolved.recycle()
    return color
}

@ColorInt
fun Context.getDynamicAttributeColor(
    @AttrRes attrId: Int,
    @ColorInt default: Int = -1,
    @StyleRes theme: Int = com.google.android.material.R.style.ThemeOverlay_Material3_DynamicColors_DayNight,
): Int {
    return DynamicColors.wrapContextIfAvailable(this, theme).getAttributeColor(attrId, default)
}

fun Context?.findActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext != null) {
        if (currentContext is AppCompatActivity) return currentContext
        if (currentContext !is ContextWrapper) break
        currentContext = currentContext.baseContext
    }
    return null
}

fun Context.isBackgroundRestricted(checkUnrestrictedBattery: Boolean = false): Boolean {
    val manufacturer = Build.MANUFACTURER.lowercase()
    val restrictiveManufacturers = listOf(
        "huawei",
        "xiaomi",
        "redmi",
        "oppo",
        "vivo",
        "realme",
        "blackview",
        "tecno",
        "infinix",
    )
    val isRestrictive = manufacturer in restrictiveManufacturers

    val activityManager: ActivityManager? = getSystemService()
    val isBackgroundRestricted = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P &&
        activityManager?.isBackgroundRestricted == true

    val checkBattery = checkUnrestrictedBattery || manufacturer in listOf("samsung", "motorola")
    val isBatteryUsageRestricted = if (checkBattery) {
        getSystemService<PowerManager>()?.isIgnoringBatteryOptimizations(packageName) != true
    } else {
        false
    }

    return isRestrictive || isBackgroundRestricted || isBatteryUsageRestricted
}

fun widgetPinningNotAvailable(): Boolean {
    val manufacturer = Build.MANUFACTURER.lowercase()
    val notAvailable = listOf(
        "oppo",
        "realme",
    )

    return manufacturer in notAvailable
}

fun Context.getMaxBitmapWidgetDimension(
    coerceMaxMemory: Boolean = false,
    coerceDimension: Boolean = false,
): Int {
    Timber.d("Calculating max dimension (coerceMaxMemory=$coerceMaxMemory, coerceDimension=$coerceDimension)")

    val displayMetrics: DisplayMetrics = resources.displayMetrics
    val maxMemoryAllowed: Int = if (coerceMaxMemory) {
        6_912_000 // `RemoteViews` have a maximum allowed memory for bitmaps
    } else {
        (displayMetrics.heightPixels * displayMetrics.widthPixels * 4 * 1.5).roundToInt()
    }
    val maxMemoryDimension: Int = sqrt(maxMemoryAllowed / 4 / displayMetrics.density).roundToInt()
    val maxDimension: Int = if (coerceDimension) {
        maxMemoryDimension.coerceAtMost(maximumValue = PhotoWidget.MAX_WIDGET_DIMENSION)
    } else {
        maxMemoryDimension
    }

    Timber.d("Max dimension allowed: $maxDimension (maxMemoryAllowed=$maxMemoryAllowed)")

    return maxDimension
}
