package com.wreckingball.magicdex.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wreckingball.magicdex.database.CardDao
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.network.NetworkStatus
import org.koin.core.KoinComponent
import org.koin.core.inject

class MagicCardsRepository(cardDao: CardDao) : KoinComponent {
    private val boundaryCallback: MagicBoundaryCallback by inject()

    val networkStatus: MutableLiveData<NetworkStatus> = boundaryCallback.networkStatus
    val cardList: LiveData<PagedList<Card>>
    private val config = PagedList.Config.Builder()
        .setPageSize(CARD_PAGE_SIZE)
        .setEnablePlaceholders(false)
        .build()

    init {
        val dataSourceFactory: DataSource.Factory<Int, Card> = cardDao.getAllPaged()
        cardList = LivePagedListBuilder<Int, Card>(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }
}