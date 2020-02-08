package com.wreckingball.magicdex.network

class MagicRSSApiService(private val retrofit: RSSRetrofit) {
    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.getRSSRetrofit().create(serviceClass)
    }
}
