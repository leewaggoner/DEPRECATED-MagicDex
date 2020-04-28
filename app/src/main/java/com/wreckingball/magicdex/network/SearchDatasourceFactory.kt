package com.wreckingball.magicdex.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.wreckingball.magicdex.models.Card
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf


class SearchDataSourceFactory(searchString: String) : DataSource.Factory<Int, Card>(), KoinComponent {
    val networkStatus: MutableLiveData<NetworkStatus>
    val searchLiveDataSource = MutableLiveData<SearchDataSource>()
    private val dataSource: SearchDataSource by inject { parametersOf(searchString) }

    init {
        networkStatus = dataSource.networkStatus
    }

    override fun create(): DataSource<Int, Card> {
        searchLiveDataSource.postValue(dataSource)
        return dataSource
    }
}