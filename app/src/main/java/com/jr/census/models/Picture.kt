package com.jr.census.models

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pictures", foreignKeys = [ForeignKey(entity = Property::class, parentColumns = arrayOf("id"),
childColumns = arrayOf("propertyID"), onDelete = ForeignKey.CASCADE)])
class Picture (){

    @Ignore
    constructor(location : String, order : Int, propertyID: Int) : this(){
        this.location = location
        this.order = order
        this.propertyID = propertyID
    }
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    @SerializedName("idServer")
    @ColumnInfo(name = "idServer")
    var idServer : String? = null
    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title : String? = null
    @SerializedName("subtitle")
    @ColumnInfo(name = "subtitle")
    var subtitle : String? = null
    @SerializedName("location")
    @ColumnInfo(name = "location")
    lateinit var location : String
    @SerializedName("description")
    @ColumnInfo(name = "description")
    var description : String? = null
    @SerializedName("order")
    @ColumnInfo(name = "order" )
    var order : Int = 0
    @SerializedName("propertyID")
    @ColumnInfo(name = "propertyID", index = true)
    var propertyID : Int = 0

}