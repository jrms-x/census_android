package com.jr.census.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "properties", foreignKeys = [ForeignKey(entity = Block::class,
    parentColumns = arrayOf("id"), childColumns = arrayOf("idBlock"), onDelete = ForeignKey.CASCADE)]
)
class Property() : Parcelable {

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
    @SerializedName("block")
    @ColumnInfo(name="idBlock", index = true)
    var idBlock : Int = 0
    @ColumnInfo(name = "property")
    @SerializedName("property")
    var property : Long? = null
    @ColumnInfo(name = "derived")
    @SerializedName("derived")
    var derived : Long? = null
    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name : String? = null
    @SerializedName("street")
    @ColumnInfo(name = "street")
    var street : String? = null
    @SerializedName("suburb")
    @ColumnInfo(name = "suburb")
    var suburb : String? = null
    @ColumnInfo(name = "bis")
    @SerializedName("bis")
    var bis : String? = null
    @ColumnInfo(name = "number")
    @SerializedName("number")
    var number : String? = null
    @ColumnInfo(name = "interior")
    @SerializedName("interior")
    var interior : String? = null
    @ColumnInfo(name = "building")
    @SerializedName("building")
    var building : String? = null
    @ColumnInfo(name = "floor")
    @SerializedName("floor")
    var floor : String? = null
    @ColumnInfo(name = "block_city")
    @SerializedName("block_city")
    var blockCity : String? = null
    @ColumnInfo(name = "lot")
    @SerializedName("lot")
    var lot : String? = null
    @ColumnInfo(name = "customer_rating")
    @SerializedName("customer_rating")
    var customerRating : String? = null
    @ColumnInfo(name = "location")
    @SerializedName("location")
    var location : String? = null
    @ColumnInfo(name = "serialNumber")
    @SerializedName("serial_number")
    var serialNumber : String? = null
    @ColumnInfo(name = "typeService")
    @SerializedName("type_service")
    var typeService : Int? = null

    @Ignore
    @SerializedName("census_data")
    var census : PropertyCensusInformation? = null

    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    var latitude : Double? = null

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    var longitude : Double? = null


    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        idBlock = parcel.readInt()
        property = parcel.readValue(Long::class.java.classLoader) as? Long
        derived = parcel.readValue(Long::class.java.classLoader) as? Long
        name = parcel.readString()
        street = parcel.readString()
        suburb = parcel.readString()
        bis = parcel.readString()
        number = parcel.readString()
        interior = parcel.readString()
        building = parcel.readString()
        floor = parcel.readString()
        blockCity = parcel.readString()
        lot = parcel.readString()
        customerRating = parcel.readString()
        location = parcel.readString()
        serialNumber = parcel.readString()
        typeService = parcel.readValue(Int::class.java.classLoader) as? Int
        census = parcel.readParcelable(PropertyCensusInformation::class.java.classLoader)
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeInt(idBlock)
        dest?.writeLong(property ?: 0)
        dest?.writeLong(derived ?: 0)
        dest?.writeString(name)
        dest?.writeString(street)
        dest?.writeString(suburb)
        dest?.writeString(bis)
        dest?.writeString(number)
        dest?.writeString(interior)
        dest?.writeString(building)
        dest?.writeString(blockCity)
        dest?.writeString(lot)
        dest?.writeString(customerRating)
        dest?.writeString(location)
        dest?.writeString(serialNumber)
        dest?.writeInt(typeService ?: 0)
        dest?.writeParcelable(census, flags)
        dest?.writeDouble(latitude ?: 0.0)
        dest?.writeDouble(longitude ?: 0.0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Property> {
        override fun createFromParcel(parcel: Parcel): Property {
            return Property(parcel)
        }

        override fun newArray(size: Int): Array<Property?> {
            return arrayOfNulls(size)
        }
    }

}