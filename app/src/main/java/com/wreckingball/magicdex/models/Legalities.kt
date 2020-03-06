package com.wreckingball.magicdex.models

import java.io.Serializable

data class Legalities (
    var format: String?,
    var legality: String?
) : Serializable {
    companion object {
        private const  val serialVersionUID = -31L
    }
}