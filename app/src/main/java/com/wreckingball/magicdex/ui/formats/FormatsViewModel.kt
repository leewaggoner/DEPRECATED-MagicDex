package com.wreckingball.magicdex.ui.formats

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wreckingball.magicdex.models.Formats
import com.wreckingball.magicdex.repositories.FormatsRepository

class FormatsViewModel(private val formatsRepository: FormatsRepository) : ViewModel() {
    val formats = MutableLiveData<Formats>()

    init {
        formatsRepository.formats = formats
    }

    fun getFormats() {
        if (formats.value == null) {
            formats.value = Formats()
            formatsRepository.fetchFormats()
        }
    }

    fun handleFormatsClick(context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://magic.wizards.com/en/game-info/gameplay/rules-and-formats/formats")))
    }
}