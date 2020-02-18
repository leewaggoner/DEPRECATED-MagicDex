package com.wreckingball.magicdex.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.wreckingball.magicdex.models.ForeignNames
import com.wreckingball.magicdex.models.Legalities
import com.wreckingball.magicdex.models.Rulings

class Converters {
//    companion object {
        @TypeConverter
        fun fromStringList(value: List<String>?) : String {
            return if (value == null || value.isEmpty()) "[]" else Gson().toJson(value)
        }

        @TypeConverter
        fun toStringList(value: String?) : List<String> {
            var list: List<String> = emptyList()
            if (value != null && value.isNotEmpty()) {
                list = Gson().fromJson(value, Array<String>::class.java).asList()
            }
            return list
        }

        @TypeConverter
        fun fromRulingsList(value: List<Rulings>?) : String {
            return if (value == null || value.isEmpty()) "[]" else Gson().toJson(value)
        }

        @TypeConverter
        fun toRulingsList(value: String?) : List<Rulings> {
            var list: List<Rulings> = emptyList()
            if (value != null && value.isNotEmpty()) {
                list = Gson().fromJson(value, Array<Rulings>::class.java).asList()
            }
            return list
        }

        @TypeConverter
        fun fromForeignNamesList(value: List<ForeignNames>?) : String {
            return if (value == null || value.isEmpty()) "[]" else Gson().toJson(value)
        }

        @TypeConverter
        fun toForeignNamesList(value: String?) : List<ForeignNames> {
            var list: List<ForeignNames> = emptyList()
            if (value != null && value.isNotEmpty()) {
                list = Gson().fromJson(value, Array<ForeignNames>::class.java).asList()
            }
            return list
        }

        @TypeConverter
        fun fromLegalitiesList(value: List<Legalities>?) : String {
            return if (value == null || value.isEmpty()) "[]" else Gson().toJson(value)
        }

        @TypeConverter
        fun toLegalitiesList(value: String?) : List<Legalities> {
            var list: List<Legalities> = emptyList()
            if (value != null && value.isNotEmpty()) {
                list = Gson().fromJson(value, Array<Legalities>::class.java).asList()
            }
            return list
        }
//    }
}