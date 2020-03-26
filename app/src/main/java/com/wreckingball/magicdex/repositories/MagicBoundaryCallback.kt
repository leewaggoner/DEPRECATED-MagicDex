package com.wreckingball.magicdex.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.google.gson.Gson
import com.wreckingball.magicdex.database.CardDao
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.models.MagicCards
import com.wreckingball.magicdex.network.*
import com.wreckingball.magicdex.utils.PreferencesWrapper
import com.wreckingball.magicdex.utils.Retryable
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.Response

const val CARD_PAGE_SIZE = 50
private const val TAG = "MagicBoundaryCallback"
private const val PAGE_NUMBER_KEY = "currentPageNumber"

class MagicBoundaryCallback(private val cardDao: CardDao,
                            private val cardService: CardService) :
                            PagedList.BoundaryCallback<Card>(),
                            KoinComponent {
    val networkStatus = MutableLiveData<NetworkStatus>()
    private val preferencesWrapper: PreferencesWrapper by inject()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val status = NetworkStatus()
    private var lastPageNumber = -1

    override fun onZeroItemsLoaded() {
        networkStatus.value = status
        fetchCardPage()
    }

    private fun fetchCardPage() {
        status.status = LOADING
        networkStatus.value = status
        var currentPageNumber = preferencesWrapper.getInt(PAGE_NUMBER_KEY, 1)
        Log.d(TAG, "Network call https://api.magicthegathering.io/v1/cards?page=$currentPageNumber")
        val call = cardService.getCards(currentPageNumber, CARD_PAGE_SIZE)
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
                            //update current page number
                            currentPageNumber++
                            preferencesWrapper.putInt(PAGE_NUMBER_KEY, currentPageNumber)
                            //put cards in database
                            loadCards(fetchedCards.cards)
                            if (lastPageNumber == -1) {
                                lastPageNumber = getLastPage(response.headers().get("link"))
                                Log.d(TAG, "lastPageNumber=$lastPageNumber")
                            }
                        }
                    } else {
                        Log.d(TAG, "Failed fetching cards")
                        status.status = ERROR
                        status.message = "Failed fetching cards"
                        networkStatus.value = status
                    }
                } else {
                    val errorStr = response.errorBody()?.string()
                    Log.d(TAG, "Network response unsuccessful: $errorStr")
                    try {
                        val message: ApiError = Gson().fromJson(errorStr, ApiError::class.java)
                        Log.d(TAG, "Network response unsuccessful: $message")
                        status.status = ERROR
                        status.message = "Error code ${message.status}: ${message.message}"
                        networkStatus.value = status
                    } catch (e: Exception) {
                        Log.d(TAG, "Gson failed", e)
                        status.status = ERROR
                        status.message = "Gson failed"
                        networkStatus.value = status
                    }
                }
            }

            override fun onFailure(call: Call<MagicCards>, t: Throwable) {
                Log.d(TAG, "Call failed", t)
                val retryable: Retryable = object : Retryable {
                    override fun retry() {
                        fetchCardPage()
                    }
                }
                handleError(retryable, t)
            }
        })
    }


    override fun onItemAtEndLoaded(itemAtEnd: Card) {
        fetchCardPage()
    }

    private fun loadCards(cards: List<Card>) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                cardDao.insertAll(cards)
            }
        }
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