package com.wreckingball.magicdex.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.google.gson.Gson
import com.wreckingball.magicdex.models.Card

private const val TAG = "SearchDataSource"

class SearchDataSource(private val searchString: String, private val cardService: CardService) : PageKeyedDataSource<Int, Card>() {
    val networkStatus: MutableLiveData<NetworkStatus> = MutableLiveData<NetworkStatus>()
    private val status = NetworkStatus()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Card>) {
        status.status = LOADING
        networkStatus.postValue(status)
        Log.d(
            TAG,
            "Network call https://api.magicthegathering.io/v1/cards?name=${searchString}&page=0"
        )
        val call = cardService.searchCards(searchString, 0, params.requestedLoadSize)
        val response = call.execute()
        if (response.isSuccessful) {
            Log.d(TAG, "Network response successful")
            if (response.body() != null) {
                Log.d(TAG, "Successfully fetched cards")
                val cards = response.body()
                cards?.let { fetchedCards ->
                    status.status = SUCCESS
                    networkStatus.postValue(status)
                    val nextKey =
                        if (getLastPage(response.headers().get("link")) == null) null else 2
                    callback.onResult(fetchedCards.cards, null, nextKey)
                }
            } else {
                Log.d(TAG, "Failed fetching cards")
                status.status = ERROR
                status.message = "Failed fetching cards"
                networkStatus.postValue(status)
            }
        } else {
            val errorStr = response.errorBody()?.string()
            Log.d(TAG, "Network response unsuccessful: $errorStr")
            try {
                val message: ApiError = Gson().fromJson(errorStr, ApiError::class.java)
                Log.d(TAG, "Network response unsuccessful: $message")
                status.status = ERROR
                status.message = "Error code ${message.status}: ${message.message}"
                networkStatus.postValue(status)
            } catch (e: Exception) {
                Log.d(TAG, "Gson failed", e)
                status.status = ERROR
                status.message = "Gson failed"
                networkStatus.postValue(status)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Card>) {
        status.status = LOADING
        networkStatus.postValue(status)
        Log.d(TAG, "Network call https://api.magicthegathering.io/v1/cards?name=${searchString}&page=${params.key}")
        val call = cardService.searchCards(searchString, params.key, params.requestedLoadSize)
        val response = call.execute()
        if (response.isSuccessful) {
            Log.d(TAG, "Network response successful")
            if (response.body() != null) {
                Log.d(TAG, "Successfully fetched cards")
                val cards = response.body()
                cards?.let { fetchedCards ->
                    status.status = SUCCESS
                    networkStatus.postValue(status)
                    val nextKey = if (getLastPage(response.headers().get("link")) == null)  null else params.key + 1
                    callback.onResult(fetchedCards.cards, nextKey)
                }
            } else {
                Log.d(TAG, "Failed fetching cards")
                status.status = ERROR
                status.message = "Failed fetching cards"
                networkStatus.postValue(status)
            }
        } else {
            val errorStr = response.errorBody()?.string()
            Log.d(TAG, "Network response unsuccessful: $errorStr")
            try {
                val message: ApiError = Gson().fromJson(errorStr, ApiError::class.java)
                Log.d(TAG, "Network response unsuccessful: $message")
                status.status = ERROR
                status.message = "Error code ${message.status}: ${message.message}"
                networkStatus.postValue(status)
            } catch (e: Exception) {
                Log.d(TAG, "Gson failed", e)
                status.status = ERROR
                status.message = "Gson failed"
                networkStatus.postValue(status)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Card>) {
        TODO("Not yet implemented")
    }

    private fun getLastPage(links: String?) : String? {
        return getLastPageLink(links)
    }

    private fun getLastPageLink(links: String?) :String? {
        var last: String? = null
        if (links != null) {
            val linkList = links.split(",")
            for (link: String in linkList) {
                if (link.contains("last", true)) {
                    last = link
                    break
                }
            }
        }
        return last
    }
}