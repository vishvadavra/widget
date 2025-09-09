package com.epic.widgetwall.home

import androidx.annotation.StringRes
import com.epic.widgetwall.R

data class HelpArticle(
    @StringRes val title: Int,
    @StringRes val body: Int,
) {

    companion object {

        fun allArticles(): List<HelpArticle> = listOf(
            HelpArticle(title = R.string.help_article_title_1, body = R.string.help_article_body_1),
            HelpArticle(title = R.string.help_article_title_2, body = R.string.help_article_body_2),
            HelpArticle(title = R.string.help_article_title_3, body = R.string.help_article_body_3),
            HelpArticle(title = R.string.help_article_title_4, body = R.string.help_article_body_4),
        )
    }
}
