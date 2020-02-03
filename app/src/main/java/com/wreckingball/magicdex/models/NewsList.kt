package com.wreckingball.magicdex.models

import androidx.room.Ignore
import com.wreckingball.magicdex.network.BaseApiResponse
import com.wreckingball.magicdex.network.LOADING

class NewsList(@Ignore override var status: Int = LOADING, @Ignore override var message: String = "")
    : ArrayList<News>(),
    BaseApiResponse
