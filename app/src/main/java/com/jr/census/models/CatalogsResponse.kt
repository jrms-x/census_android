package com.jr.census.models

import com.google.gson.annotations.SerializedName

class CatalogsResponse {
    @SerializedName("anomalies")
    var anomalies : List<Anomaly>? = null
    @SerializedName("meterStatus")
    var meterStatus : List<MeterStatus>? = null
    @SerializedName("meterBrands")
    var meterBrands : List<MeterBrand>? = null
    @SerializedName("typesCharges")
    var typesCharges : List<ChargeType>? = null
    @SerializedName("typesProperties")
    var typesProperties : List<PropertyType>? = null
    @SerializedName("typesProtections")
    var typesProtections : List<ProtectionType>? = null
    @SerializedName("typesOutlet")
    var typesOutlets : List<OutletType>? = null
}