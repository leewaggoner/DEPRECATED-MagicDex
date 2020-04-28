package com.wreckingball.magicdex.repositories

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wreckingball.magicdex.callbacks.CARD_PAGE_SIZE
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.network.SearchDataSource
import com.wreckingball.magicdex.network.SearchDataSourceFactory
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

private const val TAG = "SetsRepository"

class SearchRepository(searchString: String) : KoinComponent {
    private val dataSourceFactory: SearchDataSourceFactory by inject { parametersOf(searchString) }
    private var searchLiveDataSource: LiveData<SearchDataSource>
    val networkStatus = dataSourceFactory.networkStatus
    val cardList: LiveData<PagedList<Card>>

    init {
        searchLiveDataSource = dataSourceFactory.searchLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(CARD_PAGE_SIZE)
            .build()

        cardList = LivePagedListBuilder(dataSourceFactory, config)
            .build()
    }
}