package com.wreckingball.magicdex.ui.sets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wreckingball.magicdex.models.Sets
import com.wreckingball.magicdex.repositories.SetsRepository

class SetsViewModel(private val setsRepository: SetsRepository) : ViewModel() {
    val sets = MutableLiveData<Sets>()

    init {
        setsRepository.sets = sets
    }

    fun getSets() {
        sets.value = Sets()
        setsRepository.getSets()
    }
}