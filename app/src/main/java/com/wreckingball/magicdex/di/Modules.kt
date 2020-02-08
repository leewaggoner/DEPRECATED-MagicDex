package com.wreckingball.magicdex.di

import androidx.room.Room
import com.wreckingball.magicdex.database.NewsDatabase
import com.wreckingball.magicdex.network.*
import com.wreckingball.magicdex.repositories.MagicDataSource
import com.wreckingball.magicdex.repositories.MagicRssRepository
import com.wreckingball.magicdex.ui.home.HomeViewModel
import com.wreckingball.magicdex.ui.magicdex.MagicDexViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module(override = true) {
    viewModel { HomeViewModel(get()) }
    viewModel { MagicDexViewModel(get()) }
    single { MagicDataSource(get()) }
    single { MagicRssRepository(get(), get()) }
    single { get<NewsDatabase>().newsDao() }
    single { Room.databaseBuilder(androidApplication(), NewsDatabase::class.java, "news_db").build() }
    single { provideRssService(get()) }
    single { MagicRSSApiService(get()) }
    single { RSSRetrofit() }
    single { provideCardService(get()) }
    single { MagicCardApiService(get()) }
    single { CardRetrofit() }
}

fun provideRssService(magicRSSApiService: MagicRSSApiService) : RssService {
    return magicRSSApiService.createService(RssService::class.java)
}

fun provideCardService(magicCardApiService: MagicCardApiService) : CardService {
    return magicCardApiService.createService(CardService::class.java)
}
