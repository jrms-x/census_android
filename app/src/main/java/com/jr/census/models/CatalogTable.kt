package com.jr.census.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

abstract class CatalogTable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name : String? = null
}