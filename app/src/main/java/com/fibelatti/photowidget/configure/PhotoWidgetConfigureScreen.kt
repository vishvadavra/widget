@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.epic.widgetwall.configure

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.layout.LazyLayoutCacheWindow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.epic.widgetwall.R
import com.epic.widgetwall.model.LocalPhoto
import com.epic.widgetwall.model.PhotoWidget
import com.epic.widgetwall.model.PhotoWidgetAspectRatio
import com.epic.widgetwall.model.PhotoWidgetBorder
import com.epic.widgetwall.model.PhotoWidgetColors
import com.epic.widgetwall.model.PhotoWidgetShapeBuilder
import com.epic.widgetwall.model.PhotoWidgetSource
import com.epic.widgetwall.model.PhotoWidgetTapActions
import com.epic.widgetwall.model.canSort
import com.epic.widgetwall.platform.ComposeBottomSheetDialog
import com.epic.widgetwall.platform.formatPercent
import com.epic.widgetwall.platform.formatRangeValue
import com.epic.widgetwall.platform.withRoundedCorners
import com.epic.widgetwall.platform.getColorPalette
import com.epic.widgetwall.platform.getDynamicAttributeColor
import com.epic.widgetwall.platform.colorForType
import com.epic.widgetwall.model.borderPercent
import com.epic.widgetwall.preferences.DefaultPicker
import com.epic.widgetwall.preferences.PickerDefault
import com.epic.widgetwall.ui.LoadingIndicator
import com.epic.widgetwall.ui.ShapedPhoto
import com.epic.widgetwall.ui.SliderSmallThumb
import com.epic.widgetwall.ui.ColoredShape
import com.epic.widgetwall.ui.GradientButton
import com.epic.widgetwall.ui.foundation.dpToPx
import com.epic.widgetwall.ui.foundation.fadingEdges
import com.epic.widgetwall.ui.preview.AllPreviews
import com.epic.widgetwall.ui.text.AutoSizeText
import com.epic.widgetwall.ui.theme.ExtendedTheme
import kotlin.math.roundToInt
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyGridState

@Composable
fun PhotoWidgetConfigureScreen(
    photoWidget: PhotoWidget,
    isUpdating: Boolean,
    selectedPhoto: LocalPhoto?,
    isProcessing: Boolean,
    onNavClick: () -> Unit,
    onAspectRatioClick: () -> Unit,
    onCropClick: (LocalPhoto) -> Unit,
    onRemoveClick: (LocalPhoto) -> Unit,
    onMoveLeftClick: (LocalPhoto) -> Unit,
    onMoveRightClick: (LocalPhoto) -> Unit,
    onChangeSource: (currentSource: PhotoWidgetSource, syncedDir: Set<Uri>) -> Unit,
    onPhotoPickerClick: () -> Unit,
    onDirPickerClick: () -> Unit,
    onPhotoClick: (LocalPhoto) -> Unit,
    onReorderFinished: (List<LocalPhoto>) -> Unit,
    onRemovedPhotoClick: (LocalPhoto) -> Unit,
    onTapActionPickerClick: (PhotoWidgetTapActions) -> Unit,
    onShapeChange: (String) -> Unit,
    onCornerRadiusChange: (Int) -> Unit,
    onBorderChange: (PhotoWidgetBorder) -> Unit,
    onOpacityChange: (Float) -> Unit,
    onSaturationChange: (Float) -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onOffsetChange: (horizontalOffset: Int, verticalOffset: Int) -> Unit,
    onPaddingChange: (Int) -> Unit,
    onAddToHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val localContext = LocalContext.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val blurRadius by animateDpAsState(
            targetValue = if (isProcessing) 10.dp else 0.dp,
            label = "ProcessingBlur",
        )

        PhotoWidgetConfigureContent(
            photoWidget = photoWidget,
            isUpdating = isUpdating,
            selectedPhoto = selectedPhoto,
            onNavClick = onNavClick,
            onMoveLeftClick = onMoveLeftClick,
            onMoveRightClick = onMoveRightClick,
            onCropClick = onCropClick,
            onRemoveClick = onRemoveClick,
            onChangeSource = onChangeSource,
            onPhotoPickerClick = onPhotoPickerClick,
            onDirPickerClick = onDirPickerClick,
            onPhotoClick = onPhotoClick,
            onReorderFinished = onReorderFinished,
            onRemovedPhotoClick = onRemovedPhotoClick,
            onTapActionPickerClick = onTapActionPickerClick,
            onShapeChange = onShapeChange,
            onCornerRadiusChange = onCornerRadiusChange,
            onBorderChange = onBorderChange,
            onOpacityChange = onOpacityChange,
            onSaturationChange = onSaturationChange,
            onBrightnessChange = onBrightnessChange,
            onPaddingChange = onPaddingChange,
            onAddToHomeClick = onAddToHomeClick,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = blurRadius),
        )

        AnimatedVisibility(
            visible = isProcessing,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                LoadingIndicator(
                    modifier = Modifier.size(72.dp),
                )
            }
        }
    }
}

@Composable
private fun PhotoWidgetConfigureContent(
    photoWidget: PhotoWidget,
    isUpdating: Boolean,
    selectedPhoto: LocalPhoto?,
    onNavClick: () -> Unit,
    onCropClick: (LocalPhoto) -> Unit,
    onRemoveClick: (LocalPhoto) -> Unit,
    onMoveLeftClick: (LocalPhoto) -> Unit,
    onMoveRightClick: (LocalPhoto) -> Unit,
    onChangeSource: (currentSource: PhotoWidgetSource, syncedDir: Set<Uri>) -> Unit,
    onPhotoPickerClick: () -> Unit,
    onDirPickerClick: () -> Unit,
    onPhotoClick: (LocalPhoto) -> Unit,
    onReorderFinished: (List<LocalPhoto>) -> Unit,
    onRemovedPhotoClick: (LocalPhoto) -> Unit,
    onTapActionPickerClick: (PhotoWidgetTapActions) -> Unit,
    onShapeChange: (String) -> Unit,
    onCornerRadiusChange: (Int) -> Unit,
    onBorderChange: (PhotoWidgetBorder) -> Unit,
    onOpacityChange: (Float) -> Unit,
    onSaturationChange: (Float) -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onPaddingChange: (Int) -> Unit,
    onAddToHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        if (maxWidth < 840.dp) {
            Column {
                PhotoWidgetViewer(
                    photoWidget = photoWidget,
                    selectedPhoto = selectedPhoto,
                    onNavClick = onNavClick,
                    onCropClick = onCropClick,
                    onRemoveClick = onRemoveClick,
                    onMoveLeftClick = onMoveLeftClick,
                    onMoveRightClick = onMoveRightClick,
                    onAddPhotoClick = onPhotoPickerClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    editingControlsInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Start),
                )

                PhotoWidgetEditor(
                    photoWidget = photoWidget,
                    isUpdating = isUpdating,
                    onChangeSource = onChangeSource,
                    onPhotoPickerClick = onPhotoPickerClick,
                    onDirPickerClick = onDirPickerClick,
                    onPhotoClick = onPhotoClick,
                    onReorderFinished = onReorderFinished,
                    onRemovedPhotoClick = onRemovedPhotoClick,
                    onShapeChange = onShapeChange,
                    onCornerRadiusChange = onCornerRadiusChange,
                    onBorderChange = onBorderChange,
                    onOpacityChange = onOpacityChange,
                    onSaturationChange = onSaturationChange,
                    onBrightnessChange = onBrightnessChange,
                    onPaddingChange = onPaddingChange,
                    onTapActionPickerClick = onTapActionPickerClick,
                    onAddToHomeClick = onAddToHomeClick,
                    contentWindowInsets = WindowInsets.navigationBars,
                )
            }
        } else {
            Row {
                PhotoWidgetViewer(
                    photoWidget = photoWidget,
                    selectedPhoto = selectedPhoto,
                    onNavClick = onNavClick,
                    onCropClick = onCropClick,
                    onRemoveClick = onRemoveClick,
                    onMoveLeftClick = onMoveLeftClick,
                    onMoveRightClick = onMoveRightClick,
                    onAddPhotoClick = onPhotoPickerClick,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = 0.4f),
                    editingControlsInsets = WindowInsets.safeDrawing
                        .only(sides = WindowInsetsSides.Start + WindowInsetsSides.Bottom),
                )

                PhotoWidgetEditor(
                    photoWidget = photoWidget,
                    isUpdating = isUpdating,
                    onChangeSource = onChangeSource,
                    onPhotoPickerClick = onPhotoPickerClick,
                    onDirPickerClick = onDirPickerClick,
                    onPhotoClick = onPhotoClick,
                    onReorderFinished = onReorderFinished,
                    onRemovedPhotoClick = onRemovedPhotoClick,
                    onShapeChange = onShapeChange,
                    onCornerRadiusChange = onCornerRadiusChange,
                    onBorderChange = onBorderChange,
                    onOpacityChange = onOpacityChange,
                    onSaturationChange = onSaturationChange,
                    onBrightnessChange = onBrightnessChange,
                    onPaddingChange = onPaddingChange,
                    onTapActionPickerClick = onTapActionPickerClick,
                    onAddToHomeClick = onAddToHomeClick,
                    contentWindowInsets = WindowInsets.systemBars
                        .union(WindowInsets.displayCutout.only(WindowInsetsSides.End)),
                )
            }
        }
    }
}

// region Sections
@Composable
private fun PhotoWidgetViewer(
    photoWidget: PhotoWidget,
    selectedPhoto: LocalPhoto?,
    onNavClick: () -> Unit,
    onCropClick: (LocalPhoto) -> Unit,
    onRemoveClick: (LocalPhoto) -> Unit,
    onMoveLeftClick: (LocalPhoto) -> Unit,
    onMoveRightClick: (LocalPhoto) -> Unit,
    onAddPhotoClick: () -> Unit,
    modifier: Modifier = Modifier,
    editingControlsInsets: WindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Start),
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CurrentPhotoViewer(
            photo = selectedPhoto,
            aspectRatio = photoWidget.aspectRatio,
            shapeId = photoWidget.shapeId,
            modifier = Modifier.fillMaxSize(),
            cornerRadius = photoWidget.cornerRadius,
            border = photoWidget.border,
            colors = photoWidget.colors,
        )

        IconButton(
            onClick = onNavClick,
            shapes = IconButtonDefaults.shapes(),
            modifier = Modifier
                .align(Alignment.TopStart)
                .safeDrawingPadding(),
            colors = IconButtonDefaults.iconButtonColors().copy(
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
            )
        }

        // Show Add Photo button in center when no photos
        if (photoWidget.photos.isEmpty()) {
            GradientButton(
                text = "Add Photo",
                onClick = onAddPhotoClick,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // Show Add More button above photos when photos exist
            GradientButton(
                text = "Add More",
                onClick = onAddPhotoClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 10.dp, vertical = 30.dp)
            )
        }

        if (selectedPhoto != null) {
            EditingControls(
                onCropClick = { onCropClick(selectedPhoto) },
                onRemoveClick = { onRemoveClick(selectedPhoto) },
                showMoveControls = photoWidget.canSort,
                moveLeftEnabled = photoWidget.photos.indexOf(selectedPhoto) != 0,
                onMoveLeftClick = { onMoveLeftClick(selectedPhoto) },
                moveRightEnabled = photoWidget.photos.indexOf(selectedPhoto) < photoWidget.photos.size - 1,
                onMoveRightClick = { onMoveRightClick(selectedPhoto) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .windowInsetsPadding(editingControlsInsets),
            )
        }
    }
}

@Composable
private fun PhotoWidgetEditor(
    photoWidget: PhotoWidget,
    isUpdating: Boolean,
    onChangeSource: (currentSource: PhotoWidgetSource, syncedDir: Set<Uri>) -> Unit,
    onPhotoPickerClick: () -> Unit,
    onDirPickerClick: () -> Unit,
    onPhotoClick: (LocalPhoto) -> Unit,
    onReorderFinished: (List<LocalPhoto>) -> Unit,
    onRemovedPhotoClick: (LocalPhoto) -> Unit,
    onShapeChange: (String) -> Unit,
    onCornerRadiusChange: (Int) -> Unit,
    onBorderChange: (PhotoWidgetBorder) -> Unit,
    onOpacityChange: (Float) -> Unit,
    onSaturationChange: (Float) -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onPaddingChange: (Int) -> Unit,
    onTapActionPickerClick: (PhotoWidgetTapActions) -> Unit,
    onAddToHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentWindowInsets: WindowInsets = WindowInsets.navigationBars,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .windowInsetsPadding(contentWindowInsets),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ConfigureTabs(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) { tab ->
            val tabContentScrollState = rememberScrollState()
            val tabContentModifier = Modifier
                .fillMaxSize()
                .verticalScroll(tabContentScrollState)
                .padding(vertical = 16.dp)
                .fadingEdges(scrollState = tabContentScrollState)

            when (tab) {
                ConfigureTab.CONTENT -> {
                    ContentTab(
                        photoWidget = photoWidget,
                        onChangeSource = onChangeSource,
                        onPhotoPickerClick = onPhotoPickerClick,
                        onDirPickerClick = onDirPickerClick,
                        onPhotoClick = onPhotoClick,
                        onReorderFinished = onReorderFinished,
                        onRemovedPhotoClick = onRemovedPhotoClick,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                ConfigureTab.APPEARANCE -> {
                    AppearanceTab(
                        photoWidget = photoWidget,
                        onShapeChange = onShapeChange,
                        onCornerRadiusChange = onCornerRadiusChange,
                        onBorderChange = onBorderChange,
                        onOpacityChange = onOpacityChange,
                        onSaturationChange = onSaturationChange,
                        onBrightnessChange = onBrightnessChange,
                        onPaddingChange = onPaddingChange,
                        onTapActionPickerClick = onTapActionPickerClick,
                        modifier = tabContentModifier,
                    )
                }
            }
        }

        Button(
            onClick = onAddToHomeClick,
            shapes = ButtonDefaults.shapes(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(
                    id = if (isUpdating) {
                        R.string.photo_widget_configure_save_changes
                    } else {
                        R.string.photo_widget_configure_add_to_home
                    },
                ),
            )
        }
    }
}
// endregion Sections

// region Tabs
@Composable
private fun ContentTab(
    photoWidget: PhotoWidget,
    onChangeSource: (currentSource: PhotoWidgetSource, syncedDir: Set<Uri>) -> Unit,
    onPhotoPickerClick: () -> Unit,
    onDirPickerClick: () -> Unit,
    onPhotoClick: (LocalPhoto) -> Unit,
    onReorderFinished: (List<LocalPhoto>) -> Unit,
    onRemovedPhotoClick: (LocalPhoto) -> Unit,
    modifier: Modifier = Modifier,
) {
    PhotoPicker(
        source = photoWidget.source,
        onChangeSource = { onChangeSource(photoWidget.source, photoWidget.syncedDir) },
        photos = photoWidget.photos,
        canSort = photoWidget.canSort,
        onPhotoPickerClick = onPhotoPickerClick,
        onDirPickerClick = onDirPickerClick,
        onPhotoClick = onPhotoClick,
        onReorderFinished = onReorderFinished,
        removedPhotos = photoWidget.removedPhotos,
        onRemovedPhotoClick = onRemovedPhotoClick,
        aspectRatio = photoWidget.aspectRatio,
        shapeId = photoWidget.shapeId,
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun AppearanceTab(
    photoWidget: PhotoWidget,
    onShapeChange: (String) -> Unit,
    onCornerRadiusChange: (Int) -> Unit,
    onBorderChange: (PhotoWidgetBorder) -> Unit,
    onOpacityChange: (Float) -> Unit,
    onSaturationChange: (Float) -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onPaddingChange: (Int) -> Unit,
    onTapActionPickerClick: (PhotoWidgetTapActions) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Tap Action Picker at the top
        PickerDefault(
            title = stringResource(id = R.string.widget_defaults_tap_action),
            currentValue = buildString {
                appendLine(stringResource(id = photoWidget.tapActions.left.label))
                appendLine(stringResource(id = photoWidget.tapActions.center.label))
                appendLine(stringResource(id = photoWidget.tapActions.right.label))
            },
            onClick = { onTapActionPickerClick(photoWidget.tapActions) },
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        if (PhotoWidgetAspectRatio.SQUARE == photoWidget.aspectRatio) {
            InlineShapePicker(
                title = stringResource(id = R.string.widget_defaults_shape),
                currentShapeId = photoWidget.shapeId,
                onShapeChange = onShapeChange,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        } else if (PhotoWidgetAspectRatio.FILL_WIDGET != photoWidget.aspectRatio) {
            InlineCornerRadiusPicker(
                title = stringResource(id = R.string.widget_defaults_corner_radius),
                currentValue = photoWidget.cornerRadius,
                onValueChange = onCornerRadiusChange,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (PhotoWidgetAspectRatio.FILL_WIDGET != photoWidget.aspectRatio) {
            InlineBorderPicker(
                title = stringResource(R.string.photo_widget_configure_border),
                currentBorder = photoWidget.border,
                onBorderChange = onBorderChange,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        InlineOpacityPicker(
            title = stringResource(id = R.string.widget_defaults_opacity),
            currentValue = photoWidget.colors.opacity,
            onValueChange = onOpacityChange,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        InlineSaturationPicker(
            title = stringResource(R.string.widget_defaults_saturation),
            currentValue = photoWidget.colors.saturation,
            onValueChange = onSaturationChange,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        InlineBrightnessPicker(
            title = stringResource(R.string.widget_defaults_brightness),
            currentValue = photoWidget.colors.brightness,
            onValueChange = onBrightnessChange,
            modifier = Modifier.padding(horizontal = 16.dp),
        )


        if (PhotoWidgetAspectRatio.FILL_WIDGET != photoWidget.aspectRatio) {
            InlinePaddingPicker(
                title = stringResource(id = R.string.photo_widget_configure_padding),
                currentValue = photoWidget.padding,
                onValueChange = onPaddingChange,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

// endregion Tabs

// region Components
@Composable
private fun CurrentPhotoViewer(
    photo: LocalPhoto?,
    aspectRatio: PhotoWidgetAspectRatio,
    shapeId: String,
    cornerRadius: Int,
    border: PhotoWidgetBorder,
    colors: PhotoWidgetColors,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        val gradientColors = listOf(
            Color.White,
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
        )

        val largeRadialGradient = object : ShaderBrush() {
            override fun createShader(size: Size): Shader = RadialGradientShader(
                colors = gradientColors,
                center = size.center,
                radius = maxOf(size.height, size.width),
                colorStops = listOf(0f, 0.9f),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(largeRadialGradient)
                .blur(10.dp),
        )

        if (photo != null) {
            ShapedPhoto(
                photo = photo,
                aspectRatio = aspectRatio,
                shapeId = shapeId,
                cornerRadius = cornerRadius,
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Start),
                    )
                    .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 48.dp)
                    .fillMaxHeight(),
                colors = colors,
                border = border,
            )
        }
    }
}

@Composable
private fun EditingControls(
    onCropClick: () -> Unit,
    onRemoveClick: () -> Unit,
    showMoveControls: Boolean,
    moveLeftEnabled: Boolean,
    onMoveLeftClick: () -> Unit,
    moveRightEnabled: Boolean,
    onMoveRightClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSources: Array<MutableInteractionSource> = remember {
        Array(size = 4) { MutableInteractionSource() }
    }

    ButtonGroup(
        overflowIndicator = {},
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {


        customItem(
            buttonGroupContent = {
                FilledTonalIconButton(
                    onClick = onCropClick,
                    modifier = Modifier.animateWidth(interactionSources[1]),
                    interactionSource = interactionSources[1],
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_crop),
                        contentDescription = stringResource(id = R.string.photo_widget_configure_menu_crop),
                    )
                }
            },
            menuContent = {},
        )

        customItem(
            buttonGroupContent = {
                FilledTonalIconButton(
                    onClick = onRemoveClick,
                    modifier = Modifier.animateWidth(interactionSources[2]),
                    interactionSource = interactionSources[2],
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_trash),
                        contentDescription = stringResource(id = R.string.photo_widget_configure_menu_remove),
                    )
                }
            },
            menuContent = {},
        )
    }
}
// endregion Components

// region Pickers
@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun PhotoPicker(
    source: PhotoWidgetSource,
    onChangeSource: () -> Unit,
    photos: List<LocalPhoto>,
    canSort: Boolean,
    onPhotoPickerClick: () -> Unit,
    onDirPickerClick: () -> Unit,
    onPhotoClick: (LocalPhoto) -> Unit,
    onReorderFinished: (List<LocalPhoto>) -> Unit,
    removedPhotos: List<LocalPhoto>,
    onRemovedPhotoClick: (LocalPhoto) -> Unit,
    aspectRatio: PhotoWidgetAspectRatio,
    shapeId: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        val localHaptics = LocalHapticFeedback.current

        val currentPhotos by rememberUpdatedState(photos.toMutableStateList())
        val cacheWindow = LazyLayoutCacheWindow(aheadFraction = .5f, behindFraction = .5f)
        val lazyGridState = rememberLazyGridState(cacheWindow = cacheWindow)
        val reorderableLazyGridState = rememberReorderableLazyGridState(lazyGridState) { from, to ->
            currentPhotos.apply {
                this[to.index] = this[from.index].also {
                    this[from.index] = this[to.index]
                }
            }
            localHaptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
        }

        if (currentPhotos.isEmpty()) {
            // Empty state when no photos are selected
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_default),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp),
                )
                Text(
                    text = "No photos selected",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Add photos to create your widget",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 4),
                modifier = Modifier
                    .fillMaxSize()
                    .fadingEdges(scrollState = lazyGridState),
                state = lazyGridState,
                contentPadding = PaddingValues(start = 16.dp, top = 68.dp, end = 16.dp, bottom = 200.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(currentPhotos, key = { photo -> photo }) { photo ->
                    ReorderableItem(reorderableLazyGridState, key = photo) {
                        ShapedPhoto(
                            photo = photo,
                            aspectRatio = PhotoWidgetAspectRatio.SQUARE,
                            shapeId = if (PhotoWidgetAspectRatio.SQUARE == aspectRatio) {
                                shapeId
                            } else {
                                PhotoWidget.DEFAULT_SHAPE_ID
                            },
                            cornerRadius = PhotoWidget.DEFAULT_CORNER_RADIUS,
                            modifier = Modifier
                                .animateItem()
                                .longPressDraggableHandle(
                                    enabled = canSort,
                                    onDragStarted = {
                                        localHaptics.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
                                    },
                                    onDragStopped = {
                                        onReorderFinished(currentPhotos)
                                        localHaptics.performHapticFeedback(HapticFeedbackType.GestureEnd)
                                    },
                                )
                                .aspectRatio(ratio = 1f)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    role = Role.Image,
                                    onClick = { onPhotoClick(photo) },
                                ),
                        )
                    }
                }
            }
        }



        AnimatedVisibility(
            visible = removedPhotos.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            RemovedPhotosPicker(
                title = when (source) {
                    PhotoWidgetSource.PHOTOS -> stringResource(
                        R.string.photo_widget_configure_photos_pending_deletion,
                    )

                    PhotoWidgetSource.DIRECTORY -> stringResource(R.string.photo_widget_configure_photos_excluded)
                },
                photos = removedPhotos,
                onPhotoClick = onRemovedPhotoClick,
                aspectRatio = aspectRatio,
                shapeId = shapeId,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to Color.Transparent,
                                0.2f to MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                                1f to MaterialTheme.colorScheme.background,
                            ),
                        ),
                    )
                    .padding(top = 32.dp),
            )
        }
    }
}

@Composable
private fun RemovedPhotosPicker(
    title: String,
    photos: List<LocalPhoto>,
    onPhotoClick: (LocalPhoto) -> Unit,
    aspectRatio: PhotoWidgetAspectRatio,
    shapeId: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(photos, key = { it.photoId }) { photo ->
                ShapedPhoto(
                    photo = photo,
                    aspectRatio = PhotoWidgetAspectRatio.SQUARE,
                    shapeId = if (PhotoWidgetAspectRatio.SQUARE == aspectRatio) {
                        shapeId
                    } else {
                        PhotoWidget.DEFAULT_SHAPE_ID
                    },
                    cornerRadius = PhotoWidget.DEFAULT_CORNER_RADIUS,
                    modifier = Modifier
                        .animateItem()
                        .fillMaxWidth()
                        .aspectRatio(ratio = 1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            role = Role.Image,
                            onClick = { onPhotoClick(photo) },
                        ),
                    colors = PhotoWidgetColors(saturation = 0f),
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PaddingPicker(
    currentValue: Int,
    onApplyClick: (newValue: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    DefaultPicker(
        title = stringResource(id = R.string.photo_widget_configure_padding),
        modifier = modifier,
    ) {
        var value by remember(currentValue) { mutableIntStateOf(currentValue) }

        Image(
            bitmap = rememberSampleBitmap()
                .withRoundedCorners(radius = PhotoWidget.DEFAULT_CORNER_RADIUS.dpToPx())
                .asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding((value * PhotoWidget.POSITIONING_MULTIPLIER).dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Slider(
                value = value.toFloat(),
                onValueChange = { value = it.toInt() },
                modifier = Modifier.weight(1f),
                valueRange = 0f..20f,
                thumb = { SliderSmallThumb() },
            )

            Text(
                text = "$value",
                modifier = Modifier.width(40.dp),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Button(
            onClick = { onApplyClick(value) },
            shapes = ButtonDefaults.shapes(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(text = stringResource(id = R.string.photo_widget_action_apply))
        }
    }
}
// endregion Pickers

// region Inline Pickers
@Composable
private fun InlineShapePicker(
    title: String,
    currentShapeId: String,
    onShapeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().run {
            copy(containerColor = containerColor.copy(alpha = 0.6f))
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            LazyHorizontalGrid(
                rows = GridCells.Fixed(count = 3),
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(PhotoWidgetShapeBuilder.shapes) { shape ->
                    val isSelected = shape.id == currentShapeId

                    // Create a variety of white shade colors
                    val whiteShades = listOf(
                        Color(0xFFFFFFFF), // Pure white
                        Color(0xFFF8F9FA), // Very light gray
                        Color(0xFFE9ECEF), // Light gray
                        Color(0xFFDEE2E6), // Medium light gray
                        Color(0xFFCED4DA), // Medium gray
                        Color(0xFFADB5BD), // Darker gray
                        Color(0xFF6C757D), // Dark gray
                        Color(0xFF495057), // Very dark gray
                        Color(0xFF343A40), // Almost black
                        Color(0xFF212529), // Black
                    )

                    // Cycle through white shades based on shape index
                    val colorIndex = PhotoWidgetShapeBuilder.shapes.indexOf(shape) % whiteShades.size
                    val baseColor = whiteShades[colorIndex]

                    val shapeColor = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        baseColor
                    }

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable { onShapeChange(shape.id) }
                            .then(
                                if (isSelected) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        ColoredShape(
                            shapeId = shape.id,
                            color = shapeColor,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InlineCornerRadiusPicker(
    title: String,
    currentValue: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().run {
            copy(containerColor = containerColor.copy(alpha = 0.6f))
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Slider(
                    value = currentValue.toFloat(),
                    onValueChange = { onValueChange(it.roundToInt()) },
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 0f..128f,
                    thumb = { SliderSmallThumb() },
                )

                Text(
                    text = "$currentValue",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun InlineBorderPicker(
    title: String,
    currentBorder: PhotoWidgetBorder,
    onBorderChange: (PhotoWidgetBorder) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().run {
            copy(containerColor = containerColor.copy(alpha = 0.6f))
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            // Border type selection
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
            ) {
                items(PhotoWidgetBorder.entries) { border ->
                    val isSelected = border.serializedName == currentBorder.serializedName
                    OutlinedButton(
                        onClick = { onBorderChange(border) },
                        modifier = Modifier.height(36.dp),
                        colors = if (isSelected) {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            )
                        } else {
                            ButtonDefaults.outlinedButtonColors()
                        },
                    ) {
                        Text(
                            text = stringResource(id = border.label),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InlineOpacityPicker(
    title: String,
    currentValue: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().run {
            copy(containerColor = containerColor.copy(alpha = 0.6f))
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Slider(
                    value = currentValue,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 0f..100f,
                    thumb = { SliderSmallThumb() },
                )

                Text(
                    text = formatPercent(value = currentValue, fractionDigits = 0),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InlineSaturationPicker(
    title: String,
    currentValue: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().run {
            copy(containerColor = containerColor.copy(alpha = 0.6f))
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Slider(
                    value = PhotoWidgetColors.pickerSaturation(currentValue),
                    onValueChange = { onValueChange(PhotoWidgetColors.persistenceSaturation(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = -100f..100f,
                    thumb = { SliderSmallThumb() },
                )

                Text(
                    text = formatRangeValue(value = PhotoWidgetColors.pickerSaturation(currentValue)),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InlineBrightnessPicker(
    title: String,
    currentValue: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().run {
            copy(containerColor = containerColor.copy(alpha = 0.6f))
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Slider(
                    value = currentValue,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = -100f..100f,
                    thumb = { SliderSmallThumb() },
                )

                Text(
                    text = formatRangeValue(value = currentValue),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InlinePaddingPicker(
    title: String,
    currentValue: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().run {
            copy(containerColor = containerColor.copy(alpha = 0.6f))
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    bitmap = rememberSampleBitmap()
                        .withRoundedCorners(radius = PhotoWidget.DEFAULT_CORNER_RADIUS.dpToPx())
                        .asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding((currentValue * PhotoWidget.POSITIONING_MULTIPLIER).dp),
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Slider(
                        value = currentValue.toFloat(),
                        onValueChange = { onValueChange(it.roundToInt()) },
                        modifier = Modifier.fillMaxWidth(),
                        valueRange = 0f..20f,
                        thumb = { SliderSmallThumb() },
                    )

                    Text(
                        text = "$currentValue",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
// endregion Inline Pickers

// region Previews
@Composable
@AllPreviews
private fun PhotoWidgetConfigureScreenPreview() {
    ExtendedTheme {
        PhotoWidgetConfigureScreen(
            photoWidget = PhotoWidget(
                photos = List(20) { index -> LocalPhoto(photoId = "photo-$index") },
            ),
            isUpdating = false,
            selectedPhoto = LocalPhoto(photoId = "photo-0"),
            isProcessing = false,
            onNavClick = {},
            onMoveLeftClick = {},
            onMoveRightClick = {},
            onAspectRatioClick = {},
            onCropClick = {},
            onRemoveClick = {},
            onChangeSource = { _, _ -> },
            onPhotoPickerClick = {},
            onDirPickerClick = {},
            onPhotoClick = {},
            onReorderFinished = {},
            onRemovedPhotoClick = {},
            onTapActionPickerClick = {},
            onShapeChange = {},
            onCornerRadiusChange = {},
            onBorderChange = {},
            onOpacityChange = {},
            onSaturationChange = {},
            onBrightnessChange = {},
            onOffsetChange = { _, _ -> },
            onPaddingChange = {},
            onAddToHomeClick = {},
        )
    }
}

@Composable
@AllPreviews
private fun PhotoWidgetConfigureScreenTallPreview() {
    ExtendedTheme {
        PhotoWidgetConfigureScreen(
            photoWidget = PhotoWidget(
                source = PhotoWidgetSource.DIRECTORY,
                photos = List(20) { index -> LocalPhoto(photoId = "photo-$index") },
                aspectRatio = PhotoWidgetAspectRatio.TALL,
                colors = PhotoWidgetColors(opacity = 80f),
            ),
            isUpdating = true,
            selectedPhoto = LocalPhoto(photoId = "photo-0"),
            isProcessing = false,
            onNavClick = {},
            onMoveLeftClick = {},
            onMoveRightClick = {},
            onAspectRatioClick = {},
            onCropClick = {},
            onRemoveClick = {},
            onChangeSource = { _, _ -> },
            onPhotoPickerClick = {},
            onDirPickerClick = {},
            onPhotoClick = {},
            onReorderFinished = {},
            onRemovedPhotoClick = {},
            onTapActionPickerClick = {},
            onShapeChange = {},
            onCornerRadiusChange = {},
            onBorderChange = {},
            onOpacityChange = {},
            onSaturationChange = {},
            onBrightnessChange = {},
            onOffsetChange = { _, _ -> },
            onPaddingChange = {},
            onAddToHomeClick = {},
        )
    }
}
// endregion Previews
