package com.wreckingball.magicdex.models

import java.io.Serializable

data class ForeignNames (
    var name: String?,
    var text: String?,
    var flavor: String?,
    var imageUrl: String?,
    var language: String?,
    var multiverseid: Int?
) : Serializable {
    companion object {
        private const  val serialVersionUID = -103L
    }
}