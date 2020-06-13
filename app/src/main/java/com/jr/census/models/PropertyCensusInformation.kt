package com.jr.census.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "census_data", foreignKeys = [ForeignKey(entity = Property::class, parentColumns =
["id"], childColumns = ["id_property"], onDelete = ForeignKey.CASCADE)],
indices = [Index(value = ["id_property"])])
class PropertyCensusInformation() : Parcelable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0
    @ColumnInfo(name = "id_anomaly")
    @SerializedName("id_anomaly")
    var idAnomaly : Int = 0
    @ColumnInfo(name = "id_meter_status")
    @SerializedName("id_meter_status")
    var idMeterStatus : Int = 0
    @ColumnInfo(name = "id_meter_brands")
    @SerializedName("id_meter_brands")
    var idMeterBrands : Int = 0
    @ColumnInfo(name = "id_type_charges")
    @SerializedName("id_type_charges")
    var idTypesCharge : Int = 0
    @ColumnInfo(name = "id_type_properties")
    @SerializedName("id_type_properties")
    var idTypesProperty : Int = 0
    @ColumnInfo(name = "id_type_protections")
    @SerializedName("id_type_protections")
    var idTypesProtections : Int = 0
    @ColumnInfo(name = "id_type_outlet")
    @SerializedName("id_type_outlet")
    var idTypesOutlet : Int = 0
    @ColumnInfo(name = "id_property")
    @SerializedName("id_property")
    var idProperty : Int = 0
    @ColumnInfo(name = "finished_census")
    var finished : Boolean = true
    @ColumnInfo(name = "year")
    var year : Int = Calendar.getInstance().get(Calendar.YEAR)

    constructor(parcel: Parcel) : this() {
        idAnomaly = parcel.readInt()
        idMeterStatus = parcel.readInt()
        idMeterBrands = parcel.readInt()
        idTypesCharge = parcel.readInt()
        idTypesProperty = parcel.readInt()
        idTypesProtections = parcel.readInt()
        idTypesOutlet = parcel.readInt()
        idProperty = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idAnomaly)
        parcel.writeInt(idMeterStatus)
        parcel.writeInt(idMeterBrands)
        parcel.writeInt(idTypesCharge)
        parcel.writeInt(idTypesProperty)
        parcel.writeInt(idTypesProtections)
        parcel.writeInt(idTypesOutlet)
        parcel.writeInt(idProperty)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertyCensusInformation> {
        override fun createFromParcel(parcel: Parcel): PropertyCensusInformation {
            return PropertyCensusInformation(parcel)
        }

        override fun newArray(size: Int): Array<PropertyCensusInformation?> {
            return arrayOfNulls(size)
        }
    }
}