package com.wreckingball.magicdex.network

import com.wreckingball.magicdex.models.Sets
import retrofit2.Call
import retrofit2.http.GET

interface SetsService {
    @GET("sets")
    fun getSets(): Call<Sets>
}