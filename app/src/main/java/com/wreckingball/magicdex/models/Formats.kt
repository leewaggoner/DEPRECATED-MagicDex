package com.wreckingball.magicdex.models

import com.wreckingball.magicdex.network.BaseApiResponse
import com.wreckingball.magicdex.network.LOADING

class Formats(override var status: Int = LOADING, override var message: String = "")
    : BaseApiResponse {
    var formats = mutableListOf<String>()
}