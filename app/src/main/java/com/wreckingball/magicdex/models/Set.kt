package com.wreckingball.magicdex.models

data class Set (
    var block: String,
    var border: String,
    var code: String,
    var name: String,
    var onlineOnly : Boolean,
    var releaseDate: String,
    var type: String
)