package com.wreckingball.magicdex.repository

import retrofit2.Converter
import retrofit2.Retrofit

class ApiService(private val baseUrl: String, converterFactory: Converter.Factory) {
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .build()


    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}