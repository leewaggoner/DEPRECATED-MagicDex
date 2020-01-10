package com.wreckingball.magicdex.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.Menu
import com.wreckingball.magicdex.models.News
import com.wreckingball.magicdex.repository.MagicRssRepository

class HomeViewModel : ViewModel() {
    private var listMenu = MutableLiveData<List<Menu>>()
    private var listNews = MutableLiveData<List<News>>()

    fun getListMenu(context: Context): LiveData<List<Menu>> {
        listMenu.value = listOf(
            Menu(1, context.resources.getString(R.string.menu_item_1), R.color.lightGreen),
            Menu(1, context.resources.getString(R.string.menu_item_2) , R.color.lightBlack),
            Menu(1, context.resources.getString(R.string.menu_item_3), R.color.lightRed),
            Menu(1, context.resources.getString(R.string.menu_item_4), R.color.lightBlue)
        )
        return listMenu
    }

    fun getListNews(): LiveData<List<News>> {
        listNews = MagicRssRepository().getRssFeed()
        return listNews
    }
}