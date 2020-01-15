package com.wreckingball.magicdex.di

import androidx.room.Room
import com.wreckingball.magicdex.BuildConfig
import com.wreckingball.magicdex.database.NewsDatabase
import com.wreckingball.magicdex.network.MagicApiService
import com.wreckingball.magicdex.network.RssService
import com.wreckingball.magicdex.repository.MagicRssRepository
import com.wreckingball.magicdex.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

val appModule = module(override = true) {
    viewModel { HomeViewModel(get(), androidApplication()) }
    single { MagicRssRepository(get(), get()) }
    single { Room.databaseBuilder(androidApplication(), NewsDatabase::class.java, "news_db").build() }
    single { get<NewsDatabase>().newsDao() }
    single { provideRssService(get()) }
    single { MagicApiService(get()) }
    single { Retrofit.Builder()
        .baseUrl(BuildConfig.MAGIC_NEWS_BASE_API)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build() }
}

fun provideRssService(magicApiService: MagicApiService) : RssService {
    return magicApiService.createService(RssService::class.java)
}
