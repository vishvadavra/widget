package com.epic.widgetwall.widget

import android.content.Context
import com.epic.widgetwall.widget.data.PhotoWidgetStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeleteStaleDataUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val photoWidgetStorage: PhotoWidgetStorage,
) {

    suspend operator fun invoke() {
        photoWidgetStorage.deleteUnusedWidgetData(existingWidgetIds = PhotoWidgetProvider.ids(context = context))
    }
}
