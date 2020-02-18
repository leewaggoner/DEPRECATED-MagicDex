package com.wreckingball.magicdex.repositories

import androidx.paging.PageKeyedDataSource
import com.wreckingball.magicdex.database.CardDao
import com.wreckingball.magicdex.models.Card


class MagicDBDataSource(private val cardDao: CardDao) :  PageKeyedDataSource<Int, Card>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Card> ) {
        val cards: List<Card> = cardDao.getAll()
        if (cards.isNotEmpty()) {
            callback.onResult(cards, null, 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Card>) {
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Card>) {
    }
}