@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.epic.widgetwall.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epic.widgetwall.ui.preview.AllPreviews
import com.epic.widgetwall.ui.theme.ExtendedTheme

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
) {
    ContainedLoadingIndicator(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(ratio = 1f),
        polygons = LoadingIndicator.Polygons,
    )
}

object LoadingIndicator {

    val Polygons = listOf(
        MaterialShapes.SoftBurst,
        MaterialShapes.Cookie9Sided,
        MaterialShapes.Pentagon,
        MaterialShapes.Pill,
        MaterialShapes.Sunny,
        MaterialShapes.Cookie4Sided,
        MaterialShapes.Oval,
        MaterialShapes.Gem,
        MaterialShapes.Clover8Leaf,
        MaterialShapes.Puffy,
        MaterialShapes.Diamond,
        MaterialShapes.Cookie4Sided,
        MaterialShapes.SoftBoom,
        MaterialShapes.Flower,
        MaterialShapes.Ghostish,
    )
}

@Composable
@AllPreviews
private fun LoadingIndicatorPreview() {
    ExtendedTheme {
        LoadingIndicator()
    }
}
