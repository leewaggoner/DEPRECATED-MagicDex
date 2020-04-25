package com.wreckingball.magicdex.repositories

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wreckingball.magicdex.callbacks.CARD_PAGE_SIZE
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.network.SearchDatasourceFactory

private const val TAG = "SetsRepository"

class SearchRepository(private val dataSourceFactory: SearchDatasourceFactory/*cardService: CardService*/) {
    var networkStatus = dataSourceFactory.networkStatus
    var cardList: LiveData<PagedList<Card>>? = null

    fun searchByName(name: String) {
        dataSourceFactory.searchString = name

        val config = PagedList.Config.Builder()
            .setPageSize(CARD_PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        cardList = LivePagedListBuilder(dataSourceFactory, config).build()
    }
}