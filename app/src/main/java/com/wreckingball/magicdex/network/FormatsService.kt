package com.wreckingball.magicdex.network

import com.wreckingball.magicdex.models.Formats
import retrofit2.Call
import retrofit2.http.GET

interface FormatsService {
    @GET("formats")
    fun getFormats(): Call<Formats>
}