package com.epic.widgetwall.model

import androidx.annotation.StringRes
import com.epic.widgetwall.R

enum class DirectorySorting(
    @StringRes val label: Int,
) {

    NEWEST_FIRST(label = R.string.photo_widget_directory_sort_newest_first),
    OLDEST_FIRST(label = R.string.photo_widget_directory_sort_oldest_first),
}
