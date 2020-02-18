package com.wreckingball.magicdex.ui.magicdex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.network.NetworkStatus
import com.wreckingball.magicdex.repositories.CARD_PAGE_SIZE
import com.wreckingball.magicdex.repositories.MagicCardsRepository
import com.wreckingball.magicdex.repositories.MagicDataSource
import org.koin.core.KoinComponent
import org.koin.core.inject

class MagicDexViewModel(private val magicCardsRepository: MagicCardsRepository) : ViewModel() {
    val networkStatus = magicCardsRepository.networkStatus
    val cardList = magicCardsRepository.cardList
}