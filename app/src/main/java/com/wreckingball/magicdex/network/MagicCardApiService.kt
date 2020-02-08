package com.wreckingball.magicdex.network

class MagicCardApiService(private val retrofit: CardRetrofit) {
    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.getCardRetrofit().create(serviceClass)
    }
}