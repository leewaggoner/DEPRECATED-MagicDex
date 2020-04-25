package com.wreckingball.magicdex.ui.search

import androidx.lifecycle.ViewModel
import com.wreckingball.magicdex.repositories.SearchRepository

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    val networkStatus = searchRepository.networkStatus
    val cardList = searchRepository.cardList

    fun searchByName(name: String) {
        searchRepository.searchByName(name)
    }
}