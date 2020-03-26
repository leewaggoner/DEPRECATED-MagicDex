package com.wreckingball.magicdex.models

import com.wreckingball.magicdex.network.BaseApiResponse
import com.wreckingball.magicdex.network.LOADING

class Sets(override var status: Int = LOADING, override var message: String = "")
    : BaseApiResponse {
    var sets: List<Set> = mutableListOf()
}