package com.epic.widgetwall.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoWidgetTapActions(
    val left: PhotoWidgetTapAction = PhotoWidgetTapAction.ViewPreviousPhoto,
    val center: PhotoWidgetTapAction = PhotoWidgetTapAction.DEFAULT,
    val right: PhotoWidgetTapAction = PhotoWidgetTapAction.ViewNextPhoto,
) : Parcelable

val PhotoWidgetTapActions.increaseBrightness: Boolean
    get() {
        return (left as? PhotoWidgetTapAction.ViewFullScreen)?.increaseBrightness == true ||
            (center as? PhotoWidgetTapAction.ViewFullScreen)?.increaseBrightness == true ||
            (right as? PhotoWidgetTapAction.ViewFullScreen)?.increaseBrightness == true
    }

val PhotoWidgetTapActions.viewOriginalPhoto: Boolean
    get() {
        return (left as? PhotoWidgetTapAction.ViewFullScreen)?.viewOriginalPhoto == true ||
            (center as? PhotoWidgetTapAction.ViewFullScreen)?.viewOriginalPhoto == true ||
            (right as? PhotoWidgetTapAction.ViewFullScreen)?.viewOriginalPhoto == true
    }

val PhotoWidgetTapActions.noShuffle: Boolean
    get() {
        return (left as? PhotoWidgetTapAction.ViewFullScreen)?.noShuffle == true ||
            (center as? PhotoWidgetTapAction.ViewFullScreen)?.noShuffle == true ||
            (right as? PhotoWidgetTapAction.ViewFullScreen)?.noShuffle == true
    }

val PhotoWidgetTapActions.keepCurrentPhoto: Boolean
    get() {
        return (left as? PhotoWidgetTapAction.ViewFullScreen)?.keepCurrentPhoto == true ||
            (center as? PhotoWidgetTapAction.ViewFullScreen)?.keepCurrentPhoto == true ||
            (right as? PhotoWidgetTapAction.ViewFullScreen)?.keepCurrentPhoto == true
    }

val PhotoWidgetTapActions.disableTap: Boolean
    get() {
        return (left as? PhotoWidgetTapAction.ToggleCycling)?.disableTap == true ||
            (center as? PhotoWidgetTapAction.ToggleCycling)?.disableTap == true ||
            (right as? PhotoWidgetTapAction.ToggleCycling)?.disableTap == true
    }
