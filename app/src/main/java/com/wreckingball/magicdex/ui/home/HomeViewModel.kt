package com.wreckingball.magicdex.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.wreckingball.magicdex.BuildConfig
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.Menu
import com.wreckingball.magicdex.models.NewsList
import com.wreckingball.magicdex.repositories.MagicRssRepository

class HomeViewModel(private val newsRepository: MagicRssRepository) : ViewModel() {
    private var menuList = MutableLiveData<List<Menu>>()
    var newsList = MutableLiveData<NewsList>()

    init {
        newsRepository.newsList = newsList
    }

    companion object {
        enum class MenuId {
            MAGIC_DEX,
            SETS,
            TYPES,
            FORMATS
        }
    }

    fun getMenuList(context: Context): LiveData<List<Menu>> {
        menuList.value = listOf(
            Menu(MenuId.MAGIC_DEX, context.resources.getString(R.string.menu_item_1), R.color.lightGreen),
            Menu(MenuId.SETS, context.resources.getString(R.string.menu_item_2) , R.color.lightBlack),
            Menu(MenuId.TYPES, context.resources.getString(R.string.menu_item_3), R.color.lightRed),
            Menu(MenuId.FORMATS, context.resources.getString(R.string.menu_item_4), R.color.lightBlue)
        )
        return menuList
    }

    fun getNewsList() {
        newsList.value = NewsList()
        newsRepository.getRssFeed()
    }

    fun viewAllNews(activity: Activity) {
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.MAGIC_NEWS_URL)))
    }

    fun searchByName(view: View, searchText: String) {
        if (searchText.isNotEmpty() && searchText.length > 1) {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(searchText)
            view.findNavController().navigate(action)
        }
    }
}