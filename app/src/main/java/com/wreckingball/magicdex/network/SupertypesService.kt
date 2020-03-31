package com.wreckingball.magicdex.network

import com.wreckingball.magicdex.models.Supertypes
import retrofit2.Call
import retrofit2.http.GET

interface SupertypesService {
    @GET("supertypes")
    fun getSupertypes(): Call<Supertypes>
}