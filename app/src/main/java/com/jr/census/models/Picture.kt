package com.jr.census.models

class Picture (){

    constructor(title : String, subtitle : String, location : String, description : String) : this(){
        this.title = title
        this.subtitle = subtitle
        this.location = location
        this.description = description
    }

    var title : String? = null
    var subtitle : String? = null
    var location : String? = null
    var description : String? = null
}