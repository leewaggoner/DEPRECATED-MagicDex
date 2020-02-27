package com.wreckingball.magicdex.ui.magicdex

import androidx.lifecycle.ViewModel
import com.wreckingball.magicdex.repositories.MagicCardsRepository

class MagicDexViewModel(private val magicCardsRepository: MagicCardsRepository) : ViewModel() {
    val networkStatus = magicCardsRepository.networkStatus
    val cardList = magicCardsRepository.cardList
}