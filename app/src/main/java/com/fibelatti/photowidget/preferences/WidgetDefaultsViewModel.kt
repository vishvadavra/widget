package com.epic.widgetwall.preferences

import androidx.lifecycle.ViewModel
import com.epic.widgetwall.model.DirectorySorting
import com.epic.widgetwall.model.PhotoWidgetAspectRatio
import com.epic.widgetwall.model.PhotoWidgetCycleMode
import com.epic.widgetwall.model.PhotoWidgetSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class WidgetDefaultsViewModel @Inject constructor(
    private val userPreferencesStorage: UserPreferencesStorage,
) : ViewModel() {

    val userPreferences: StateFlow<UserPreferences> = userPreferencesStorage.userPreferences

    fun saveDefaultAspectRatio(aspectRatio: PhotoWidgetAspectRatio) {
        userPreferencesStorage.defaultAspectRatio = aspectRatio
    }

    fun saveDefaultSource(source: PhotoWidgetSource) {
        userPreferencesStorage.defaultSource = source
    }

    fun saveDefaultShuffle(value: Boolean) {
        userPreferencesStorage.defaultShuffle = value
    }

    fun saveDefaultSorting(value: DirectorySorting) {
        userPreferencesStorage.defaultDirectorySorting = value
    }

    fun saveDefaultCycleMode(cycleMode: PhotoWidgetCycleMode) {
        userPreferencesStorage.defaultCycleMode = cycleMode
    }

    fun saveDefaultShape(value: String) {
        userPreferencesStorage.defaultShape = value
    }

    fun saveDefaultCornerRadius(value: Int) {
        userPreferencesStorage.defaultCornerRadius = value
    }

    fun saveDefaultOpacity(value: Float) {
        userPreferencesStorage.defaultOpacity = value
    }

    fun saveDefaultSaturation(value: Float) {
        userPreferencesStorage.defaultSaturation = value
    }

    fun saveDefaultBrightness(value: Float) {
        userPreferencesStorage.defaultBrightness = value
    }

    fun clearDefaults() {
        userPreferencesStorage.clearDefaults()
    }
}
