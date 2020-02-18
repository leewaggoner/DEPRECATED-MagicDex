package com.wreckingball.magicdex.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wreckingball.magicdex.database.CardDatabase
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.network.NetworkStatus
import kotlinx.coroutines.*

class MagicCardsRepository(private val magicDataSource: MagicDataSource/*, private val cardDatabase: CardDatabase*/) {
//    private var viewModelJob = Job()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    val networkStatus: MutableLiveData<NetworkStatus> = magicDataSource.networkStatus
    val cardList: LiveData<PagedList<Card>>
    private val config = PagedList.Config.Builder()
        .setPageSize(CARD_PAGE_SIZE)
        .setEnablePlaceholders(false)
        .build()

    init {
//        initDatabase()
//        val dataSourceFactory: DataSource.Factory<Int, Card> = cardDatabase.cardDao().getAllPaged()


        val dataSourceFactory = object : DataSource.Factory<Int, Card>() {
            override fun create(): DataSource<Int, Card> {
                return magicDataSource
            }
        }


        cardList = LivePagedListBuilder<Int, Card>(dataSourceFactory, config).build()
    }

    private fun initDatabase() {
//        val tempCards: MutableList<Card> = mutableListOf()
//        tempCards.add(Card(1,
//            "test",
//            emptyList(),
//            "",
//            0,
//            emptyList(),
//            List(1) {"G"},
//            "Enchantment",
//            emptyList(),
//            emptyList(),
//            emptyList(),
//            "Rare",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "0",
//            "1",
//            "1",
//            "",
//            0,
//            "",
//            emptyList(),
//            emptyList(),
//            emptyList(),
//            emptyList(),
//            "",
//            "",
//            emptyList(),
//            "0"))
//        uiScope.launch {
//            withContext(Dispatchers.IO) {
//                cardDatabase.cardDao().insertAll(tempCards)
//            }
//        }
    }
}