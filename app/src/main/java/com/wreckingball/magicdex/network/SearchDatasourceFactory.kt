package com.wreckingball.magicdex.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.wreckingball.magicdex.models.Card


class SearchDatasourceFactory(private val dataSource: SearchDataSource) : DataSource.Factory<Int, Card>() {
    lateinit var searchString: String
    var networkStatus = dataSource.networkStatus
    private var datasourceLiveData = MutableLiveData<SearchDataSource>()

    override fun create(): DataSource<Int, Card> {
        dataSource.searchString = searchString
        datasourceLiveData.postValue(dataSource)
        return dataSource
    }
}