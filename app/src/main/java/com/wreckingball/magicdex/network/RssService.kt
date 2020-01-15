package com.wreckingball.magicdex.network

import com.wreckingball.magicdex.models.RssFeed
import retrofit2.Call
import retrofit2.http.GET

interface RssService {
    @GET("rss.xml")
    fun getRssFeed(): Call<RssFeed>
}