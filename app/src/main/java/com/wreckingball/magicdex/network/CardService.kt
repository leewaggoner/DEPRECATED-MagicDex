package com.wreckingball.magicdex.network

import com.wreckingball.magicdex.models.MagicCards
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CardService {
    @GET("cards")
    fun getCards(@Query("page") page: Long,
                 @Query("pageSize") pageSize: Int): Call<MagicCards>
}