package com.wreckingball.magicdex.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wreckingball.magicdex.database.NewsDao
import com.wreckingball.magicdex.models.News
import com.wreckingball.magicdex.models.NewsList
import com.wreckingball.magicdex.models.RssFeed
import com.wreckingball.magicdex.network.ERROR
import com.wreckingball.magicdex.network.RssService
import com.wreckingball.magicdex.network.SUCCESS
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

const val MAX_NEWS_ITEMS = 20

private const val TAG = "MagicRssRepository"
class MagicRssRepository(private val newsDao: NewsDao, private val rssService: RssService) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    lateinit var newsList: MutableLiveData<NewsList>

    fun getRssFeed() : MutableLiveData<NewsList> {
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

    private suspend fun processNewsList(rssFeed: RssFeed?) : NewsList {
        val newsList = NewsList(ERROR, "RSS Feed is empty!")
        val rssItems = rssFeed?.channel?.item
        val baseLinkUrl = rssFeed?.channel?.link
        rssItems?.let { newsItems ->
            for(index in 0 until MAX_NEWS_ITEMS) {
                if (index < newsItems.size) {
                    val rssNews = newsItems[index]
                    val news = News(
                        0,
                        rssNews.title,
                        baseLinkUrl + rssNews.link,
                        rssNews.pubDateString,
                        getImageUrl(rssNews.description)
                    )
                    newsList.add(news)
                } else {
                    break
                }
            }
            newsList.status = SUCCESS

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

    private suspend fun getNewsFromDB() : NewsList {
        val newsList = NewsList(ERROR, "Could not load RSS feed from database!")
        withContext(Dispatchers.IO) {
            val list = newsDao.getNews()
            if (list.isNotEmpty()) {
                newsList.addAll(list)
                newsList.status = SUCCESS
            } else {
                newsList.message = "RSS Feed is empty!"
            }
        }
        return newsList
    }
}