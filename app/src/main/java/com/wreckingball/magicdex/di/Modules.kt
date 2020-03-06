package com.wreckingball.magicdex.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.wreckingball.magicdex.database.CardDatabase
import com.wreckingball.magicdex.database.NewsDatabase
import com.wreckingball.magicdex.network.*
import com.wreckingball.magicdex.repositories.MagicBoundaryCallback
import com.wreckingball.magicdex.repositories.MagicCardsRepository
import com.wreckingball.magicdex.repositories.MagicRssRepository
import com.wreckingball.magicdex.ui.dashboard.DashboardViewModel
import com.wreckingball.magicdex.ui.home.HomeViewModel
import com.wreckingball.magicdex.ui.magicdex.MagicDexViewModel
import com.wreckingball.magicdex.utils.PreferencesWrapper
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module(override = true) {
    viewModel { HomeViewModel(get()) }
    viewModel { MagicDexViewModel(get()) }
    viewModel { DashboardViewModel() }
    single { MagicCardsRepository(get()) }
    single { MagicRssRepository(get(), get()) }
    single { MagicBoundaryCallback(get(), get()) }
    single { get<CardDatabase>().cardDao() }
    single { Room.databaseBuilder(androidApplication(), CardDatabase::class.java, "card_db").build() }
    single { get<NewsDatabase>().newsDao() }
    single { Room.databaseBuilder(androidApplication(), NewsDatabase::class.java, "news_db").build() }
    single { provideRssService(get()) }
    single { MagicRSSApiService(get()) }
    single { RSSRetrofit() }
    single { provideCardService(get()) }
    single { MagicCardApiService(get()) }
    single { CardRetrofit() }
    single { PreferencesWrapper(getSharedPrefs(androidContext())) }
}

fun provideRssService(magicRSSApiService: MagicRSSApiService) : RssService {
    return magicRSSApiService.createService(RssService::class.java)
}

fun provideCardService(magicCardApiService: MagicCardApiService) : CardService {
    return magicCardApiService.createService(CardService::class.java)
}

private fun getSharedPrefs(context: Context) : SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
}
