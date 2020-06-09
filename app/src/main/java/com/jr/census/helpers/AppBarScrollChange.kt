package com.jr.census.helpers

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

//some ideas from here ... https://stackoverflow.com/questions/31682310/android-collapsingtoolbarlayout-collapse-listener

enum class AppBarScrollState {
    COLLAPSED, EXPANDED
}

class AppBarScrollChange(val callback: (AppBarLayout?, AppBarScrollState) -> Unit) :
    AppBarLayout.OnOffsetChangedListener {

    private var state = AppBarScrollState.EXPANDED

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == 0 && state != AppBarScrollState.EXPANDED) {
            state = AppBarScrollState.EXPANDED
            callback(appBarLayout, state)
        } else if ((appBarLayout?.totalScrollRange ?: 0) <= abs(verticalOffset) &&
            state != AppBarScrollState.COLLAPSED
        ) {
            state = AppBarScrollState.COLLAPSED
            callback(appBarLayout, state)
        }
    }
}