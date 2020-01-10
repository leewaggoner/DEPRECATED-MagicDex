package com.wreckingball.magicdex.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wreckingball.magicdex.models.News
import com.wreckingball.magicdex.models.RssFeed
import retrofit2.Call
import retrofit2.Response
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

private val TAG = "MagicRssRepository"
class MagicRssRepository {
    private var rssService: RssService =
        ApiService("https://magic.wizards.com/en/rss/", SimpleXmlConverterFactory.create())
            .createService(RssService::class.java)

    fun getRssFeed() : MutableLiveData<List<News>> {
        var newsList = MutableLiveData<List<News>>()
        Log.d(TAG, "Network call https://magic.wizards.com/en/rss/rss.xml")
        val call = rssService.getRssFeed()
        call.enqueue(object : retrofit2.Callback<RssFeed> {
            override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Network response successful")
                    newsList.value = processNewsList(response.body())
                } else {
                    try {
                        val error = response.errorBody()
                        Log.d(TAG, "Network response unsuccessful: ${error?.string()}")
                    } catch (e: Exception) {
                        Log.d(TAG, "Network response unsuccessful!", e)
                    }
                }
            }
            override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                Log.d(TAG, "Network response failed", t)
            }
        })
        return newsList
    }

    private fun processNewsList(rssFeed: RssFeed?) : MutableList<News> {
        val newsList = mutableListOf<News>()
        val rssItems = rssFeed?.channel?.item
        val baseLinkUrl = rssFeed?.channel?.link
        rssItems?.let {
            for(rssNews in it) {
                val news = News(rssNews.title, baseLinkUrl + rssNews.link, rssNews.pubDateString, getImageUrl(rssNews.description))
                newsList.add(news)
            }
        }
        return newsList
    }

    private fun getImageUrl(description: String?) : String {
        var imageUrl = ""
        description?.let {
            imageUrl = description.substringAfter("img src=\"", "")
            imageUrl = imageUrl.substringBefore("\"")
        }

        return imageUrl
    }
}