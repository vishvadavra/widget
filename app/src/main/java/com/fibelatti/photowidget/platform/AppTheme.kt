package com.epic.widgetwall.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epic.widgetwall.preferences.UserPreferences
import com.epic.widgetwall.preferences.UserPreferencesStorage
import com.epic.widgetwall.ui.theme.ExtendedTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AppTheme(
    appThemeViewModel: AppThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val userPreferences by appThemeViewModel.userPreferences.collectAsStateWithLifecycle()

    ExtendedTheme(
        dynamicColor = userPreferences.dynamicColors,
        useTrueBlack = userPreferences.useTrueBlack,
        content = content,
    )
}

@HiltViewModel
class AppThemeViewModel @Inject constructor(
    userPreferencesStorage: UserPreferencesStorage,
) : ViewModel() {

    val userPreferences: StateFlow<UserPreferences> = userPreferencesStorage.userPreferences
}
