package com.wreckingball.magicdex.network

import retrofit2.Retrofit

class MagicApiService(private val retrofit: Retrofit) {

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}
