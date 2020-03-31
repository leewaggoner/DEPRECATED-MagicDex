package com.wreckingball.magicdex.network

import com.wreckingball.magicdex.models.Subtypes
import retrofit2.Call
import retrofit2.http.GET

interface SubtypesService {
    @GET("subtypes")
    fun getSubtypes(): Call<Subtypes>
}