package com.wreckingball.magicdex.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.repositories.SearchRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class SearchViewModel(searchString: String) : ViewModel(), KoinComponent {
    private val searchRepository: SearchRepository by inject { parametersOf(searchString) }
    val networkStatus = searchRepository.networkStatus
    val cardList: LiveData<PagedList<Card>>

    init {
        cardList = searchRepository.cardList
    }
}