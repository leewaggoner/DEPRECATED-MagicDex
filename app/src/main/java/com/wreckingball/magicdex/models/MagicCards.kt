package com.wreckingball.magicdex.models

import androidx.room.Ignore
import com.wreckingball.magicdex.network.BaseApiResponse
import com.wreckingball.magicdex.network.LOADING

class MagicCards (
    var cards: ArrayList<Card>,
    @Ignore override var status: Int = LOADING,
    @Ignore override var message: String = ""
) : BaseApiResponse