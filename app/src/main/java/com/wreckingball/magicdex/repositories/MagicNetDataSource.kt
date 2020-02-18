package com.wreckingball.magicdex.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.google.gson.Gson
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.models.MagicCards
import com.wreckingball.magicdex.network.*
import com.wreckingball.magicdex.utils.Retryable
import retrofit2.Call
import retrofit2.Response

private const val TAG = "MagicDataSource"
const val CARD_PAGE_SIZE = 100

class MagicDataSource(private val cardService: CardService) : PageKeyedDataSource<Int, Card>() {
    val networkStatus = MutableLiveData<NetworkStatus>()
    private var lastPageNumber = 0
    private val status = NetworkStatus()

    init {
        networkStatus.value = NetworkStatus()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Card>) {
        Log.d(TAG, "Network call https://api.magicthegathering.io/v1/cards?page=1")
        val call = cardService.getCards(1, CARD_PAGE_SIZE)
        call.enqueue(object : retrofit2.Callback<MagicCards> {
            override fun onResponse(call: Call<MagicCards>, response: Response<MagicCards>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Network response successful")
                    if (response.body() != null) {
                        Log.d(TAG, "Successfully fetched cards")
                        val cards = response.body()
                        cards?.let { fetchedCards ->
                            status.status = SUCCESS
                            networkStatus.value = status
                            callback.onResult(fetchedCards.cards, null, 2)
                            lastPageNumber = getLastPage(response.headers().get("link"))
                            Log.d(TAG, "lastPageNumber=$lastPageNumber")
                        }
                    } else {
                        Log.d(TAG, "Failed fetching cards")
                    }
                } else {
                    val error = response.errorBody()
                    val errorStr = error?.string()
                    Log.d(TAG, "Network response unsuccessful: $errorStr")
                    try {
                        val message: Error = Gson().fromJson(errorStr, Error::class.java)
                        Log.d(TAG, "Network response unsuccessful: $message")
                    } catch (e: Exception) {
                        Log.d(TAG, "Gson failed", e)
                    }
                }
            }

            override fun onFailure(call: Call<MagicCards>, t: Throwable) {
                Log.d(TAG, "Call failed", t)
                val retryable: Retryable = object : Retryable {
                    override fun retry() {
                        loadInitial(params, callback)
                    }
                }
                handleError(retryable, t)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Card>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Card>) {
        Log.d(TAG, "Network call https://api.magicthegathering.io/v1/cards?page=${params.key}")
//        status.status = LOADING
//        networkStatus.postValue(status)
        val call = cardService.getCards(params.key, CARD_PAGE_SIZE)
        call.enqueue(object : retrofit2.Callback<MagicCards> {
            override fun onResponse(call: Call<MagicCards>, response: Response<MagicCards>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Network response successful")
                    if (response.body() != null) {
                        Log.d(TAG, "Successfully fetched cards")
                        val cards = response.body()
                        cards?.let { fetchedCards ->
                            status.status = SUCCESS
                            networkStatus.value = status
                            val nextKey = if (params.key >= lastPageNumber) null else params.key + 1
                            callback.onResult(fetchedCards.cards, nextKey)
                        }
                    } else {
                        Log.d(TAG, "Failed fetching cards")
                    }
                } else {
                    val error = response.errorBody()
                    val errorStr = error?.string()
                    Log.d(TAG, "Network response unsuccessful: $errorStr")
                    try {
                        val message: Error = Gson().fromJson(errorStr, Error::class.java)
                        Log.d(TAG, "Network response unsuccessful: $message")
                    } catch (e: Exception) {
                        Log.d(TAG, "Gson failed", e)
                    }
                }
            }

            override fun onFailure(call: Call<MagicCards>, t: Throwable) {
                Log.d(TAG, "Call failed", t)
                val retryable: Retryable = object : Retryable {
                    override fun retry() {
                        loadAfter(params, callback)
                    }
                }
                handleError(retryable, t)
            }
        })
    }

    private fun getLastPage(links: String?) : Int {
        var lastPageNumber = 0
        val lastLink = getLastPageLink(links)

        if (lastLink != null) {
            lastPageNumber = getLastPageNumber(lastLink)
        }

        return lastPageNumber
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

    private fun getLastPageNumber(last: String) : Int {
        var lastPageNumber = 0

        val parts = last.split(";")
        if (parts.size == 2 && parts[0].isNotEmpty()) {
            var lastPage = parts[0].substringAfterLast("page=", "")
            if (lastPage.isNotEmpty()) {
                lastPage = lastPage.substringBeforeLast("&", "")
                if (lastPage.isNotEmpty()) {
                    try {
                        lastPageNumber = lastPage.toInt()
                    } catch (ex: NumberFormatException) {
                        Log.e(TAG, ex.message, ex)
                    }
                }
            }
        }
        return lastPageNumber
    }

    private fun handleError(retryable: Retryable, t: Throwable) {
        val error = t.message
        status.status = ERROR
        if (error != null) {
            status.message = error
        } else {
            status.message = "Call failed"
        }
        status.retryable = retryable
        networkStatus.value = status
    }
}
