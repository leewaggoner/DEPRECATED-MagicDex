package com.wreckingball.magicdex.models

import com.wreckingball.magicdex.network.BaseApiResponse
import com.wreckingball.magicdex.network.LOADING

class SearchCards(override var status: Int = LOADING, override var message: String = "")
: BaseApiResponse {
    var cards: List<Card> = mutableListOf()
}