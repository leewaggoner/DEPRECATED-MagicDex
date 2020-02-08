package com.wreckingball.magicdex.ui.magicdex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.network.NetworkStatus
import com.wreckingball.magicdex.repositories.CARD_PAGE_SIZE
import com.wreckingball.magicdex.repositories.MagicDataSource
import org.koin.core.KoinComponent
import org.koin.core.inject

class MagicDexViewModel(private val magicDataSource: MagicDataSource/*magicCardRepository: MagicCardRepository*/) : ViewModel(), KoinComponent {
    var networkStatus = MutableLiveData<NetworkStatus>()
    private val dataSource: MagicDataSource by inject()
    private val config = PagedList.Config.Builder()
        .setPageSize(CARD_PAGE_SIZE)
        .setEnablePlaceholders(false)
        .build()

    init {
        networkStatus.value = NetworkStatus()
        magicDataSource.networkStatus = networkStatus
    }

    fun getPagedListBuilder():
            LivePagedListBuilder<Long, Card> {

        val dataSourceFactory = object : DataSource.Factory<Long, Card>() {
            override fun create(): DataSource<Long, Card> {
                return dataSource
            }
        }
        return LivePagedListBuilder<Long, Card>(dataSourceFactory, config)
    }
}