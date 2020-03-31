package com.wreckingball.magicdex.network

import com.wreckingball.magicdex.models.Types
import retrofit2.Call
import retrofit2.http.GET

interface TypesService {
    @GET("types")
    fun getTypes(): Call<Types>
}