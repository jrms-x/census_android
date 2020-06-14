package com.jr.census.view.callback

import com.jr.census.models.Picture


interface ImageListListener {
    fun onSelectEdit(picture: Picture)
    fun onSelectImage(picture: Picture)
    fun startSelection()
    fun isSelectMode() : Boolean
    fun finishSelection()
    fun canShowLoading() : Boolean
}