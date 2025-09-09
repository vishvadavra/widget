package com.epic.widgetwall.configure

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import com.epic.widgetwall.R
import com.epic.widgetwall.di.PhotoWidgetEntryPoint
import com.epic.widgetwall.di.entryPoint
import com.epic.widgetwall.model.LocalPhoto
import com.epic.widgetwall.model.getPhotoPath
import com.epic.widgetwall.platform.PhotoDecoder
import com.epic.widgetwall.platform.getMaxBitmapWidgetDimension

@SuppressLint("ComposeCompositionLocalUsage")
val LocalSamplePhoto = staticCompositionLocalOf<LocalPhoto?> { null }

@Composable
fun rememberSampleBitmap(): Bitmap {
    val localContext: Context = LocalContext.current
    val localResources: Resources = LocalResources.current
    val localPhoto: LocalPhoto? = LocalSamplePhoto.current
    val decoder: PhotoDecoder by remember {
        lazy { entryPoint<PhotoWidgetEntryPoint>(localContext).photoDecoder() }
    }
    val maxDimension: Int = remember(localContext, localResources) {
        localContext.getMaxBitmapWidgetDimension()
    }

    var bitmap: Bitmap by remember {
        mutableStateOf(BitmapFactory.decodeResource(localResources, R.drawable.image_sample))
    }

    LaunchedEffect(localPhoto) {
        localPhoto?.getPhotoPath()?.let { path ->
            decoder.decode(data = path, maxDimension = maxDimension)?.let { result ->
                bitmap = result
            }
        }
    }

    return bitmap
}
