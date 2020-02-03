package com.wreckingball.magicdex.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.Menu
import com.wreckingball.magicdex.models.NewsList
import com.wreckingball.magicdex.repository.MagicRssRepository
import org.koin.core.KoinComponent

class HomeViewModel(private val newsRepository: MagicRssRepository, application: Application) :
    AndroidViewModel(application), KoinComponent {
    private var menuList = MutableLiveData<List<Menu>>()
    var newsList = MutableLiveData<NewsList>()

    init {
        newsRepository.newsList = newsList
    }

    fun getMenuList(context: Context): LiveData<List<Menu>> {
        menuList.value = listOf(
            Menu(1, context.resources.getString(R.string.menu_item_1), R.color.lightGreen),
            Menu(1, context.resources.getString(R.string.menu_item_2) , R.color.lightBlack),
            Menu(1, context.resources.getString(R.string.menu_item_3), R.color.lightRed),
            Menu(1, context.resources.getString(R.string.menu_item_4), R.color.lightBlue)
        )
        return menuList
    }

    fun getNewsList() {
        newsList.value = NewsList()
        newsRepository.getRssFeed()
    }
}