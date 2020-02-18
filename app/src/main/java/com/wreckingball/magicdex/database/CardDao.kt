package com.wreckingball.magicdex.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wreckingball.magicdex.models.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM cards")
    fun getAll() : LiveData<List<Card>>

    @Query("SELECT * FROM cards")
    fun getAllPaged() : DataSource.Factory<Int, Card>

    @Insert
    fun insertAll(cards: List<Card>)

    @Delete
    fun delete(card: Card)
}



