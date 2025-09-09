@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.epic.widgetwall.configure

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import com.epic.widgetwall.R
import com.epic.widgetwall.model.PhotoWidget
import com.epic.widgetwall.model.PhotoWidgetTapAction
import com.epic.widgetwall.model.PhotoWidgetTapActions
import com.epic.widgetwall.model.TapActionArea
import com.epic.widgetwall.platform.ComposeBottomSheetDialog
import com.epic.widgetwall.platform.withRoundedCorners
import com.epic.widgetwall.ui.Toggle
import com.epic.widgetwall.ui.foundation.ColumnToggleButtonGroup
import com.epic.widgetwall.ui.foundation.ConnectedButtonRowItem
import com.epic.widgetwall.ui.foundation.ToggleButtonGroup
import com.epic.widgetwall.ui.foundation.dpToPx
import com.epic.widgetwall.ui.preview.AllPreviews
import com.epic.widgetwall.ui.text.AutoSizeText
import com.epic.widgetwall.ui.theme.ExtendedTheme

object PhotoWidgetTapActionPicker {

    fun show(
        context: Context,
        currentTapActions: PhotoWidgetTapActions,
        onApplyClick: (newTapActions: PhotoWidgetTapActions) -> Unit,
    ) {
        ComposeBottomSheetDialog(context) {
            skipCollapsed = false

            var tapActions by remember { mutableStateOf(currentTapActions) }
            var selectedArea by remember { mutableStateOf(TapActionArea.CENTER) }

            val appShortcutPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                val newTapAction = PhotoWidgetTapAction.AppShortcut(result.data?.component?.packageName)

                tapActions = when (selectedArea) {
                    TapActionArea.LEFT -> tapActions.copy(left = newTapAction)
                    TapActionArea.CENTER -> tapActions.copy(center = newTapAction)
                    TapActionArea.RIGHT -> tapActions.copy(right = newTapAction)
                }
            }

            val galleryAppPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                val newTapAction = PhotoWidgetTapAction.ViewInGallery(result.data?.component?.packageName)

                tapActions = tapActions.copy(
                    left = if (tapActions.left is PhotoWidgetTapAction.ViewInGallery) {
                        newTapAction
                    } else {
                        tapActions.left
                    },
                    center = if (tapActions.center is PhotoWidgetTapAction.ViewInGallery) {
                        newTapAction
                    } else {
                        tapActions.center
                    },
                    right = if (tapActions.right is PhotoWidgetTapAction.ViewInGallery) {
                        newTapAction
                    } else {
                        tapActions.right
                    },
                )
            }

            TapActionPickerContent(
                selectedArea = selectedArea,
                onSelectedAreaChange = { newArea -> selectedArea = newArea },
                currentTapAction = when (selectedArea) {
                    TapActionArea.LEFT -> tapActions.left
                    TapActionArea.CENTER -> tapActions.center
                    TapActionArea.RIGHT -> tapActions.right
                },
                onTapActionChange = { newAction ->
                    val isChangingTypes = when (selectedArea) {
                        TapActionArea.LEFT -> tapActions.left.javaClass != newAction.javaClass
                        TapActionArea.CENTER -> tapActions.center.javaClass != newAction.javaClass
                        TapActionArea.RIGHT -> tapActions.right.javaClass != newAction.javaClass
                    }

                    val action = when {
                        !isChangingTypes -> newAction

                        newAction is PhotoWidgetTapAction.ViewFullScreen -> {
                            when {
                                tapActions.left is PhotoWidgetTapAction.ViewFullScreen -> tapActions.left
                                tapActions.center is PhotoWidgetTapAction.ViewFullScreen -> tapActions.center
                                tapActions.right is PhotoWidgetTapAction.ViewFullScreen -> tapActions.right
                                else -> newAction
                            }
                        }

                        newAction is PhotoWidgetTapAction.ViewInGallery -> {
                            when {
                                tapActions.left is PhotoWidgetTapAction.ViewInGallery -> tapActions.left
                                tapActions.center is PhotoWidgetTapAction.ViewInGallery -> tapActions.center
                                tapActions.right is PhotoWidgetTapAction.ViewInGallery -> tapActions.right
                                else -> newAction
                            }
                        }

                        newAction is PhotoWidgetTapAction.ToggleCycling -> {
                            when {
                                tapActions.left is PhotoWidgetTapAction.ToggleCycling -> tapActions.left
                                tapActions.center is PhotoWidgetTapAction.ToggleCycling -> tapActions.center
                                tapActions.right is PhotoWidgetTapAction.ToggleCycling -> tapActions.right
                                else -> newAction
                            }
                        }

                        else -> newAction
                    }

                    tapActions = when (selectedArea) {
                        TapActionArea.LEFT -> tapActions.copy(left = action)
                        TapActionArea.CENTER -> tapActions.copy(center = action)
                        TapActionArea.RIGHT -> tapActions.copy(right = action)
                    }
                },
                onChooseAppShortcutClick = {
                    appShortcutPickerLauncher.launch(
                        Intent(Intent.ACTION_PICK_ACTIVITY).putExtra(
                            Intent.EXTRA_INTENT,
                            Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER),
                        ),
                    )
                },
                onChooseGalleryAppClick = {
                    galleryAppPickerLauncher.launch(
                        Intent(Intent.ACTION_PICK_ACTIVITY).putExtra(
                            Intent.EXTRA_INTENT,
                            Intent(Intent.ACTION_VIEW).setDataAndType("content://sample".toUri(), "image/*"),
                        ),
                    )
                },
                onApplyClick = {
                    onApplyClick(tapActions)
                    dismiss()
                },
            )
        }.show()
    }
}

@Composable
private fun TapActionPickerContent(
    selectedArea: TapActionArea,
    onSelectedAreaChange: (TapActionArea) -> Unit,
    currentTapAction: PhotoWidgetTapAction,
    onTapActionChange: (PhotoWidgetTapAction) -> Unit,
    onChooseAppShortcutClick: () -> Unit,
    onChooseGalleryAppClick: () -> Unit,
    onApplyClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .nestedScroll(rememberNestedScrollInteropConnection())
            .verticalScroll(rememberScrollState())
            .padding(all = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.photo_widget_configure_tap_action),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = stringResource(id = R.string.photo_widget_configure_tap_action_description),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )

        TapAreaSelector(
            selectedArea = selectedArea,
            onSelectedAreaChange = onSelectedAreaChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        )

        BoxWithConstraints(
            modifier = Modifier.padding(top = 16.dp),
        ) {
            if (maxWidth < 600.dp) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    TapAreaIndicator(
                        tapActionArea = selectedArea,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    TapOptionsPicker(
                        currentTapAction = currentTapAction,
                        onTapActionClick = onTapActionChange,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TapAreaIndicator(
                        tapActionArea = selectedArea,
                        modifier = Modifier.weight(0.4f),
                    )

                    TapOptionsPicker(
                        currentTapAction = currentTapAction,
                        onTapActionClick = onTapActionChange,
                        modifier = Modifier.weight(0.6f),
                    )
                }
            }
        }

        TapActionCustomizationContent(
            tapAction = currentTapAction,
            onTapActionChange = onTapActionChange,
            onChooseGalleryAppClick = onChooseGalleryAppClick,
            onChooseAppShortcutClick = onChooseAppShortcutClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        )

        Button(
            onClick = onApplyClick,
            shapes = ButtonDefaults.shapes(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            Text(text = stringResource(id = R.string.photo_widget_action_apply))
        }
    }
}

@Composable
private fun TapAreaSelector(
    selectedArea: TapActionArea,
    onSelectedAreaChange: (TapActionArea) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
    ) {
        TapActionArea.entries.forEachIndexed { index, area ->
            val weight by animateFloatAsState(
                targetValue = if (area == selectedArea) 1.2f else 1f,
            )

            ConnectedButtonRowItem(
                checked = area == selectedArea,
                onCheckedChange = { onSelectedAreaChange(area) },
                itemIndex = index,
                itemCount = TapActionArea.entries.size,
                label = stringResource(
                    when (area) {
                        TapActionArea.LEFT -> R.string.photo_widget_configure_tap_action_area_left
                        TapActionArea.CENTER -> R.string.photo_widget_configure_tap_action_area_center
                        TapActionArea.RIGHT -> R.string.photo_widget_configure_tap_action_area_right
                    },
                ),
                modifier = Modifier.weight(weight),
            )
        }
    }
}

@Composable
private fun TapAreaIndicator(
    tapActionArea: TapActionArea,
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ClickAreaIndicator_InfiniteTransition")
    val color by infiniteTransition.animateColor(
        initialValue = Color(0x664CAF50),
        targetValue = Color.Transparent,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "ClickAreaIndicator_ColorAnimation",
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            bitmap = rememberSampleBitmap()
                .withRoundedCorners(radius = PhotoWidget.DEFAULT_CORNER_RADIUS.dpToPx())
                .asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .alpha(.6F),
        )

        Row(
            modifier = Modifier
                .width(280.dp)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.large)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.large,
                ),
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .background(color = if (TapActionArea.LEFT == tapActionArea) color else Color.Transparent),
            )

            Box(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxHeight()
                    .background(color = if (TapActionArea.CENTER == tapActionArea) color else Color.Transparent),
            )

            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .background(color = if (TapActionArea.RIGHT == tapActionArea) color else Color.Transparent),
            )
        }
    }
}

@Composable
private fun TapOptionsPicker(
    currentTapAction: PhotoWidgetTapAction,
    onTapActionClick: (PhotoWidgetTapAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    ColumnToggleButtonGroup(
        items = PhotoWidgetTapAction.entries.map {
            ToggleButtonGroup.Item(
                id = it.serializedName,
                text = stringResource(id = it.label),
            )
        },
        onButtonClick = { item ->
            onTapActionClick(
                PhotoWidgetTapAction.fromSerializedName(item.id).let { selection ->
                    if (selection.javaClass == currentTapAction.javaClass) {
                        currentTapAction
                    } else {
                        selection
                    }
                },
            )
        },
        modifier = modifier.fillMaxWidth(),
        selectedIndex = PhotoWidgetTapAction.entries.indexOfFirst {
            it.serializedName == currentTapAction.serializedName
        },
        colors = ToggleButtonGroup.colors(unselectedButtonColor = MaterialTheme.colorScheme.surfaceContainerLow),
        iconPosition = ToggleButtonGroup.IconPosition.End,
    )
}

// region Customization Content
@Composable
private fun TapActionCustomizationContent(
    tapAction: PhotoWidgetTapAction,
    onTapActionChange: (PhotoWidgetTapAction) -> Unit,
    onChooseAppShortcutClick: () -> Unit,
    onChooseGalleryAppClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (tapAction) {
        is PhotoWidgetTapAction.None -> Unit

        is PhotoWidgetTapAction.ViewFullScreen -> {
            ViewFullScreenCustomizationContent(
                value = tapAction,
                onValueChange = onTapActionChange,
                modifier = modifier,
            )
        }

        is PhotoWidgetTapAction.ViewInGallery -> {
            ViewInGalleryCustomizationContent(
                value = tapAction,
                onChooseGalleryAppClick = onChooseGalleryAppClick,
                modifier = modifier,
            )
        }

        is PhotoWidgetTapAction.ViewNextPhoto -> Unit

        is PhotoWidgetTapAction.ViewPreviousPhoto -> Unit

        is PhotoWidgetTapAction.ChooseNextPhoto -> Unit

        is PhotoWidgetTapAction.ToggleCycling -> {
            ToggleCyclingCustomizationContent(
                value = tapAction,
                onValueChange = onTapActionChange,
                modifier = modifier,
            )
        }

        is PhotoWidgetTapAction.AppShortcut -> {
            AppPicker(
                value = tapAction.appShortcut,
                onChooseAppClick = onChooseAppShortcutClick,
                modifier = modifier,
            )
        }

        is PhotoWidgetTapAction.UrlShortcut -> {
            TextField(
                value = tapAction.url.orEmpty(),
                onValueChange = { newValue -> onTapActionChange(tapAction.copy(url = newValue)) },
                modifier = modifier,
                placeholder = { Text(text = "https://...") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            )
        }
    }
}

@Composable
private fun AppPicker(
    value: String?,
    onChooseAppClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedButton(
            onClick = onChooseAppClick,
            shapes = ButtonDefaults.shapes(),
        ) {
            Text(text = stringResource(id = R.string.photo_widget_configure_tap_action_choose_app))
        }

        value?.runCatching {
            val packageManager = LocalContext.current.packageManager
            val appInfo = packageManager.getApplicationInfo(
                value,
                PackageManager.MATCH_DEFAULT_ONLY,
            )
            val appIcon = packageManager.getApplicationIcon(appInfo).toBitmap().asImageBitmap()
            val appLabel = packageManager.getApplicationLabel(appInfo).toString()

            Image(
                bitmap = appIcon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
            )

            AutoSizeText(
                text = appLabel,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun ViewFullScreenCustomizationContent(
    value: PhotoWidgetTapAction.ViewFullScreen,
    onValueChange: (PhotoWidgetTapAction.ViewFullScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Toggle(
            title = stringResource(id = R.string.photo_widget_configure_tap_action_increase_brightness),
            checked = value.increaseBrightness,
            onCheckedChange = { onValueChange(value.copy(increaseBrightness = it)) },
        )

        Toggle(
            title = stringResource(R.string.photo_widget_configure_tap_action_view_original_photo),
            checked = value.viewOriginalPhoto,
            onCheckedChange = { onValueChange(value.copy(viewOriginalPhoto = it)) },
        )

        Toggle(
            title = stringResource(R.string.photo_widget_configure_tap_action_do_not_shuffle),
            checked = value.noShuffle,
            onCheckedChange = { onValueChange(value.copy(noShuffle = it)) },
        )

        Toggle(
            title = stringResource(R.string.photo_widget_configure_tap_action_keep_current_photo),
            checked = value.keepCurrentPhoto,
            onCheckedChange = { onValueChange(value.copy(keepCurrentPhoto = it)) },
        )

        Text(
            text = stringResource(id = R.string.photo_widget_configure_tap_action_shared_preferences),
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun ViewInGalleryCustomizationContent(
    value: PhotoWidgetTapAction.ViewInGallery,
    onChooseGalleryAppClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = buildAnnotatedString {
                append(
                    stringResource(R.string.photo_widget_configure_tap_action_gallery_description_compatibility),
                )
                appendLine()
                appendLine()
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(
                        stringResource(R.string.photo_widget_configure_tap_action_gallery_description_warning),
                    )
                }
                appendLine()
                appendLine()
                append(
                    stringResource(R.string.photo_widget_configure_tap_action_gallery_description_app_selection),
                )
            },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )

        AppPicker(
            value = value.galleryApp,
            onChooseAppClick = onChooseGalleryAppClick,
        )

        Text(
            text = stringResource(id = R.string.photo_widget_configure_tap_action_shared_selected_app),
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun ToggleCyclingCustomizationContent(
    value: PhotoWidgetTapAction.ToggleCycling,
    onValueChange: (PhotoWidgetTapAction.ToggleCycling) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(id = R.string.photo_widget_configure_tap_action_toggle_cycling_description),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )

        Toggle(
            title = stringResource(id = R.string.photo_widget_configure_tap_action_disable_tap),
            checked = value.disableTap,
            onCheckedChange = { onValueChange(value.copy(disableTap = it)) },
        )

        Text(
            text = stringResource(id = R.string.photo_widget_configure_tap_action_shared_preferences),
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}
// endregion Customization Content

@Composable
@AllPreviews
private fun PhotoWidgetTapActionPickerPreview() {
    ExtendedTheme {
        TapActionPickerContent(
            selectedArea = TapActionArea.CENTER,
            onSelectedAreaChange = {},
            currentTapAction = PhotoWidgetTapAction.DEFAULT,
            onTapActionChange = {},
            onChooseAppShortcutClick = {},
            onChooseGalleryAppClick = {},
            onApplyClick = {},
        )
    }
}
