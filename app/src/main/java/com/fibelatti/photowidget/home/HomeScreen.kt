package com.epic.widgetwall.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epic.widgetwall.ui.GradientBackground
import com.epic.widgetwall.ui.GradientFloatingActionButton
import com.epic.widgetwall.R
import com.epic.widgetwall.model.PhotoWidget
import com.epic.widgetwall.model.PhotoWidgetAspectRatio
import com.epic.widgetwall.model.PhotoWidgetStatus
import com.epic.widgetwall.ui.preview.AllPreviews
import com.epic.widgetwall.ui.theme.ExtendedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCreateNewWidgetClick: (PhotoWidgetAspectRatio) -> Unit,
    currentWidgets: List<Pair<Int, PhotoWidget>>,
    onCurrentWidgetClick: (appWidgetId: Int, canSync: Boolean, canLock: Boolean, isLocked: Boolean) -> Unit,
    onRemovedWidgetClick: (appWidgetId: Int, PhotoWidgetStatus) -> Unit,
    onDefaultsClick: () -> Unit,
    onDataSaverClick: () -> Unit,
    onAppearanceClick: () -> Unit,
    onColorsClick: () -> Unit,
    onAppLanguageClick: () -> Unit,
    onBackupClick: () -> Unit,
    onSendFeedbackClick: () -> Unit,
    onRateClick: () -> Unit,
    onShareClick: () -> Unit,
    onHelpClick: () -> Unit,
    showBackgroundRestrictionHint: Boolean,
    onBackgroundRestrictionClick: () -> Unit,
    onDismissWarningClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onViewLicensesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Photo Widgets",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    IconButton(onClick = {
                        // Open settings screen
                        onDefaultsClick() // This now opens the settings dialog
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings),
                            contentDescription = stringResource(R.string.photo_widget_home_settings),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            GradientFloatingActionButton(
                onClick = {
                    // Create new widget with default square aspect ratio
                    onCreateNewWidgetClick(PhotoWidgetAspectRatio.SQUARE)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_new_widget),
                    contentDescription = stringResource(R.string.photo_widget_home_new),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues ->
        GradientBackground(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter to show only photo widgets (no tabs)
            val photoWidgets = currentWidgets.filter { (_, widget) -> 
                widget.source == com.epic.widgetwall.model.PhotoWidgetSource.PHOTOS 
            }
            
            MyWidgetsScreen(
                widgets = photoWidgets,
                onCurrentWidgetClick = onCurrentWidgetClick,
                onRemovedWidgetClick = onRemovedWidgetClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


// region Previews
@Composable
@AllPreviews
private fun HomeScreenPreview() {
    ExtendedTheme {
        HomeScreen(
            onCreateNewWidgetClick = {},
            currentWidgets = emptyList(),
            onCurrentWidgetClick = { _, _, _, _ -> },
            onRemovedWidgetClick = { _, _ -> },
            onDefaultsClick = {},
            onDataSaverClick = {},
            onAppearanceClick = {},
            onColorsClick = {},
            onAppLanguageClick = {},
            onBackupClick = {},
            onSendFeedbackClick = {},
            onRateClick = {},
            onShareClick = {},
            onHelpClick = {},
            showBackgroundRestrictionHint = true,
            onBackgroundRestrictionClick = {},
            onDismissWarningClick = {},
            onPrivacyPolicyClick = {},
            onViewLicensesClick = {},
        )
    }
}
// endregion Previews
