package com.epic.widgetwall.configure

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epic.widgetwall.R
import com.epic.widgetwall.ui.preview.LocalePreviews
import com.epic.widgetwall.ui.preview.ThemePreviews
import com.epic.widgetwall.ui.text.AutoSizeText
import com.epic.widgetwall.ui.theme.ExtendedTheme

enum class ConfigureTab(
    @StringRes val title: Int,
) {

    CONTENT(title = R.string.photo_widget_configure_tab_content),
    APPEARANCE(title = R.string.photo_widget_configure_tab_appearance),
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
inline fun ConfigureTabs(
    modifier: Modifier = Modifier,
    tabHeight: Dp = 48.dp,
    tabContent: @Composable (ConfigureTab) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(ConfigureTab.CONTENT) }
    val selectedTabIndex by remember { derivedStateOf { ConfigureTab.entries.indexOf(selectedTab) } }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = true),
                    width = 50.dp,
                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 3.dp, bottomEnd = 3.dp),
                )
            },
            divider = {},
        ) {
            ConfigureTab.entries.forEach { tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
                    modifier = Modifier.height(tabHeight),
                ) {
                    AutoSizeText(
                        text = stringResource(tab.title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            tabContent(selectedTab)
        }
    }
}

@Composable
@ThemePreviews
@LocalePreviews
private fun ConfigureTabsPreview() {
    ExtendedTheme {
        ConfigureTabs(
            modifier = Modifier.systemBarsPadding(),
            tabContent = {},
        )
    }
}
