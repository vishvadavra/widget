package com.epic.widgetwall.configure

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.net.Uri
import com.epic.widgetwall.model.PhotoWidget
import com.epic.widgetwall.model.PhotoWidgetAspectRatio
import com.epic.widgetwall.platform.intentExtras

var Intent.appWidgetId: Int by intentExtras(
    key = AppWidgetManager.EXTRA_APPWIDGET_ID,
    default = AppWidgetManager.INVALID_APPWIDGET_ID,
)

var Intent.duplicateFromId: Int? by intentExtras()

var Intent.restoreFromId: Int? by intentExtras()

var Intent.aspectRatio: PhotoWidgetAspectRatio by intentExtras()

var Intent.sharedPhotos: List<Uri>? by intentExtras()

var Intent.backupWidget: PhotoWidget? by intentExtras()

var Intent.defaultShapeId: String? by intentExtras()
