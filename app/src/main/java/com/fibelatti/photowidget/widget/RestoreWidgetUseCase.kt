package com.epic.widgetwall.widget

import com.epic.widgetwall.model.LocalPhoto
import com.epic.widgetwall.model.PhotoWidget
import com.epic.widgetwall.model.PhotoWidgetSource
import com.epic.widgetwall.widget.data.PhotoWidgetStorage
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.flow.last

class RestoreWidgetUseCase @Inject constructor(
    private val photoWidgetStorage: PhotoWidgetStorage,
) {

    suspend operator fun invoke(
        originalWidget: PhotoWidget,
        newAppWidgetId: Int,
    ): PhotoWidget {
        require(PhotoWidgetSource.PHOTOS == originalWidget.source) {
            "Only photos widgets can be restored."
        }

        val sourceDir: File? = originalWidget.photos.firstOrNull()
            ?.croppedPhotoPath
            ?.substringBeforeLast("/", missingDelimiterValue = "")
            ?.let(::File)
            ?.takeIf { it.exists() && it.isDirectory }

        requireNotNull(sourceDir) {
            "Cannot restore widget. Source directory is missing."
        }

        photoWidgetStorage.saveWidgetSource(
            appWidgetId = newAppWidgetId,
            source = originalWidget.source,
        )

        photoWidgetStorage.importWidgetDir(
            appWidgetId = newAppWidgetId,
            sourceDir = sourceDir,
        )

        val photos: List<LocalPhoto> = photoWidgetStorage.getWidgetPhotos(appWidgetId = newAppWidgetId)
            .last()
            .current

        return originalWidget.copy(
            photos = photos,
            currentPhoto = photos.firstOrNull(),
        )
    }
}
