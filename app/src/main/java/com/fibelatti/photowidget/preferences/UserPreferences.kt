package com.epic.widgetwall.preferences

import com.epic.widgetwall.model.DirectorySorting
import com.epic.widgetwall.model.PhotoWidgetAspectRatio
import com.epic.widgetwall.model.PhotoWidgetCycleMode
import com.epic.widgetwall.model.PhotoWidgetSource

data class UserPreferences(
    val dataSaver: Boolean,
    val appearance: Appearance,
    val useTrueBlack: Boolean,
    val dynamicColors: Boolean,
    val defaultAspectRatio: PhotoWidgetAspectRatio,
    val defaultSource: PhotoWidgetSource,
    val defaultShuffle: Boolean,
    val defaultDirectorySorting: DirectorySorting,
    val defaultCycleMode: PhotoWidgetCycleMode,
    val defaultShape: String,
    val defaultCornerRadius: Int,
    val defaultOpacity: Float,
    val defaultSaturation: Float,
    val defaultBrightness: Float,
)
