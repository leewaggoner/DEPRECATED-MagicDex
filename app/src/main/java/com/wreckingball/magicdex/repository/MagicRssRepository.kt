package com.wreckingball.magicdex.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wreckingball.magicdex.database.NewsDao
import com.wreckingball.magicdex.models.News
import com.wreckingball.magicdex.models.RssFeed
import com.wreckingball.magicdex.network.RssService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

private val TAG = "MagicRssRepository"
class MagicRssRepository(private val newsDao: NewsDao, private val rssService: RssService) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getRssFeed() : MutableLiveData<List<News>> {
        val newsList = MutableLiveData<List<News>>()
        Log.d(TAG, "Network call https://magic.wizards.com/en/rss/rss.xml")
        val call = rssService.getRssFeed()
        call.enqueue(object : retrofit2.Callback<RssFeed> {
            override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                if (response.isSuccessful) {
                    //get list of news articles and add them to database
                    Log.d(TAG, "Network response successful")
                    uiScope.launch {
                        newsList.value = processNewsList(response.body())
                        Log.d(TAG, "Got news from network")
                    }
                } else {
                    //get list of news articles from database
                    uiScope.launch {
                        newsList.value = getNewsFromDB()
                        Log.d(TAG, "Got news from DB")
                    }
                    val error = response.errorBody()
                    Log.d(TAG, "Network response unsuccessful: ${error?.string()}")
                }
            }
            override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                //get list of news articles from database
                Log.d(TAG, "Network response failed", t)
                uiScope.launch {
                    newsList.value = getNewsFromDB()
                    Log.d(TAG, "Got news from DB")
                }
            }
        })
        return newsList
    }

    private suspend fun processNewsList(rssFeed: RssFeed?) : MutableList<News> {
        var newsList = mutableListOf<News>()
        val rssItems = rssFeed?.channel?.item
        val baseLinkUrl = rssFeed?.channel?.link
        rssItems?.let {
            for(rssNews in it) {
                val news = News(0, rssNews.title, baseLinkUrl + rssNews.link, rssNews.pubDateString, getImageUrl(rssNews.description))
                newsList.add(news)
            }
            if (newsList.size > 20) {
                newsList = newsList.subList(0, 20)
            }
            //add to database
            withContext(Dispatchers.IO) {
                newsDao.deleteAll()
                newsDao.insertAll(newsList)
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

    private suspend fun getNewsFromDB() : List<News> {
        var newsList = listOf<News>()
        withContext(Dispatchers.IO) {
            newsList = newsDao.getNews()
        }
        return newsList
    }
}