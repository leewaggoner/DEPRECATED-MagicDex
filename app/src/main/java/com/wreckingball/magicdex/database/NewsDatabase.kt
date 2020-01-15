package com.wreckingball.magicdex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wreckingball.magicdex.models.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao
}