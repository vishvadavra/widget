package com.epic.widgetwall.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epic.widgetwall.R
import com.epic.widgetwall.model.LocalPhoto
import com.epic.widgetwall.model.PhotoWidget
import com.epic.widgetwall.model.PhotoWidgetAspectRatio
import com.epic.widgetwall.model.PhotoWidgetColors
import com.epic.widgetwall.model.PhotoWidgetShapeBuilder
import com.epic.widgetwall.model.PhotoWidgetSource
import com.epic.widgetwall.model.PhotoWidgetStatus
import com.epic.widgetwall.model.isWidgetRemoved
import com.epic.widgetwall.model.photoCycleEnabled
import com.epic.widgetwall.ui.ColoredShape
import com.epic.widgetwall.ui.MyWidgetBadge
import com.epic.widgetwall.ui.ShapedPhoto
import com.epic.widgetwall.ui.WarningSign
import com.epic.widgetwall.ui.preview.AllPreviews
import com.epic.widgetwall.ui.text.AutoSizeText
import com.epic.widgetwall.ui.theme.ExtendedTheme

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalSharedTransitionApi::class)
fun MyWidgetsScreen(
    widgets: List<Pair<Int, PhotoWidget>>,
    onCurrentWidgetClick: (appWidgetId: Int, canSync: Boolean, canLock: Boolean, isLocked: Boolean) -> Unit,
    onRemovedWidgetClick: (appWidgetId: Int, PhotoWidgetStatus) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        val maxWidth = maxWidth

        // No filtering needed - showing all widgets passed in
        val hasDeletedWidgets = remember(widgets) {
            widgets.any { it.second.status.isWidgetRemoved }
        }

        AnimatedContent(
            targetState = widgets,
            transitionSpec = {
                fadeIn(animationSpec = tween(300, delayMillis = 90))
                    .plus(scaleIn(initialScale = 0.92f, animationSpec = tween(300, delayMillis = 90)))
                    .togetherWith(
                        fadeOut(animationSpec = tween(90))
                            .plus(scaleOut(targetScale = 0.92f, animationSpec = tween(90))),
                    )
            },
            label = "MyWidgetsScreen_content",
        ) { items ->
            if (items.isNotEmpty()) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(count = if (maxWidth < 600.dp) 2 else 4),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp, 
                        top = if (hasDeletedWidgets) 120.dp else 80.dp, 
                        end = 16.dp, 
                        bottom = 120.dp
                    ),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(items, key = { (id, _) -> id }) { (id, widget) ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    if (widget.status.isWidgetRemoved) {
                                        onRemovedWidgetClick(id, widget.status)
                                    } else {
                                        onCurrentWidgetClick(
                                            /* appWidgetId = */ id,
                                            /* canSync = */ widget.source == PhotoWidgetSource.DIRECTORY,
                                            /* canLock = */ widget.photoCycleEnabled,
                                            /* isLocked = */ PhotoWidgetStatus.LOCKED == widget.status,
                                        )
                                    }
                                },
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            ShapedPhoto(
                                photo = widget.currentPhoto,
                                aspectRatio = widget.aspectRatio,
                                shapeId = widget.shapeId,
                                cornerRadius = widget.cornerRadius,
                                modifier = Modifier.fillMaxSize(),
                                colors = widget.colors,
                                border = widget.border,
                                isLoading = widget.isLoading,
                            )

                            when {
                                PhotoWidgetStatus.LOCKED == widget.status -> {
                                    MyWidgetBadge(
                                        text = stringResource(R.string.photo_widget_home_locked_label),
                                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                        contentColor = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(bottom = 8.dp),
                                    )
                                }

                                widget.status.isWidgetRemoved -> {
                                    MyWidgetBadge(
                                        text = stringResource(R.string.photo_widget_home_removed_label),
                                        backgroundColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                                        modifier = Modifier.padding(bottom = 8.dp),
                                        icon = painterResource(R.drawable.ic_trash_clock)
                                            .takeIf { PhotoWidgetStatus.REMOVED == widget.status },
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 32.dp, 
                            top = if (hasDeletedWidgets) 160.dp else 120.dp, 
                            end = 32.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_default),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp),
                    )

                    Text(
                        text = "Your photo widgets will appear here. Add your first photo to get started, then customize shape, border, and style to make it yours.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }

        // Tabs removed - only showing photos

        if (hasDeletedWidgets) {
            WarningSign(
                text = stringResource(id = R.string.photo_widget_home_removed_widgets_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(start = 32.dp, top = 16.dp, end = 32.dp),
            )
        }
    }
}

// region Previews
@Composable
@AllPreviews
private fun MyWidgetsScreenPreview() {
    ExtendedTheme {
        val allShapeIds = PhotoWidgetShapeBuilder.shapes.map { it.id }
        val opacities = listOf(70f, 85f, 100f)

        MyWidgetsScreen(
            widgets = List(size = 10) { index ->
                val status = when (index) {
                    2 -> PhotoWidgetStatus.REMOVED
                    3 -> PhotoWidgetStatus.KEPT
                    else -> PhotoWidgetStatus.ACTIVE
                }

                index to PhotoWidget(
                    photos = listOf(LocalPhoto(photoId = "photo-1")),
                    aspectRatio = when {
                        index % 3 == 0 -> PhotoWidgetAspectRatio.WIDE
                        index % 2 == 0 -> PhotoWidgetAspectRatio.TALL
                        else -> PhotoWidgetAspectRatio.SQUARE
                    },
                    shapeId = allShapeIds.random(),
                    colors = PhotoWidgetColors(opacity = opacities.random()),
                    status = status,
                    deletionTimestamp = if (PhotoWidgetStatus.REMOVED == status) 1 else -1,
                )
            },
            onCurrentWidgetClick = { _, _, _, _ -> },
            onRemovedWidgetClick = { _, _ -> },
        )
    }
}

@Composable
@AllPreviews
private fun MyWidgetsScreenEmptyPreview() {
    ExtendedTheme {
        MyWidgetsScreen(
            widgets = emptyList(),
            onCurrentWidgetClick = { _, _, _, _ -> },
            onRemovedWidgetClick = { _, _ -> },
        )
    }
}
// endregion Previews
