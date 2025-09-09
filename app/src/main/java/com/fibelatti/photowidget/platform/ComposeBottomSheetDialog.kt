package com.epic.widgetwall.platform

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import com.epic.widgetwall.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class ComposeBottomSheetDialog(
    context: Context,
    content: @Composable ComposeBottomSheetDialog.() -> Unit,
) : BottomSheetDialog(context, R.style.AppTheme_BottomSheetDialog) {

    var skipCollapsed: Boolean
        get() = behavior.skipCollapsed
        set(value) {
            behavior.skipCollapsed = value
        }

    init {
        behavior.peekHeight = 1200.dp.value.toInt()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        skipCollapsed = true

        setViewTreeOwners()

        setContentView(
            ComposeView(context).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    AppTheme {
                        content()
                    }
                }
            },
        )
    }
}
