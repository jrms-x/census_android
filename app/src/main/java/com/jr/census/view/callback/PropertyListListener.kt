package com.jr.census.view.callback

import com.jr.census.models.Property

interface PropertyListListener {
    fun onClickProperty(property : Property)
}