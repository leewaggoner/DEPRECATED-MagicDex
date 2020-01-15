package com.wreckingball.magicdex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.wreckingball.magicdex.models.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getNews(): List<News>

    @Insert(onConflict = REPLACE)
    fun insertAll(cities: List<News>)

    @Query("DELETE FROM news")
    fun deleteAll()
}