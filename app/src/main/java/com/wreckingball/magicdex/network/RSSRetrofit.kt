package com.wreckingball.magicdex.network

import com.wreckingball.magicdex.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class RSSRetrofit {
    fun getRSSRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MAGIC_NEWS_BASE_API)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }
}