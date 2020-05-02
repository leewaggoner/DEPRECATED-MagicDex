package com.wreckingball.magicdex.ui.dashboard

import androidx.lifecycle.ViewModel
import com.wreckingball.magicdex.models.Card

class DashboardViewModel : ViewModel() {
    lateinit var card: Card

    fun getManaList() : List<String> {
        var manaList = listOf<String>()
        card.manaCost?.let {manaCost ->
            manaList = manaCost.split("}")
        }
        return manaList
    }

    fun getManaName(mana: String) : String {
        var cleanMana = mana.replace("{", "")
        cleanMana = cleanMana.replace("/", "")
        return cleanMana
    }
}