package com.epic.widgetwall.model

data class WidgetPhotos(
    val current: List<LocalPhoto>,
    val excluded: List<LocalPhoto>,
)

fun WidgetPhotos.allWidgetPhotos(): List<LocalPhoto> = current + excluded
