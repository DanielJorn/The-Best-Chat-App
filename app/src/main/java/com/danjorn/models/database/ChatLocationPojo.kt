package com.danjorn.models.database

class ChatLocationPojo {

    var id: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0

    constructor(id: String, lat: Double, lon: Double) {
        this.id = id
        this.lat = lat
        this.lon = lon
    }

    constructor()
}