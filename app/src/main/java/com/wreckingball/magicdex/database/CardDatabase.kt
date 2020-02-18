package com.wreckingball.magicdex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wreckingball.magicdex.models.Card

@Database(entities = [Card::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao() : CardDao
}