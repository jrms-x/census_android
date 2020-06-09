package com.jr.census.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocks")
open class Block {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int = 0
}

class BlockPropertiesCount : Block(){
    var propertiesCount : Long = 0
}