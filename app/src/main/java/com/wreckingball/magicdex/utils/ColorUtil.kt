package com.wreckingball.magicdex.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.wreckingball.magicdex.R
import java.util.*

class ColorUtil(val context: Context) {
    fun getCardColor(colorIdentity: List<String>?) : Int {
        val type = colorIdentity?.getOrNull(0)
        val color = when (type?.toUpperCase(Locale.US)) {
            "G" -> R.color.lightGreen
            "U" -> R.color.lightBlue
            "B" -> R.color.lightBlack
            "R" -> R.color.lightRed
            "W" -> R.color.lightWhite
            else -> R.color.lightBrown
        }
        return convertColor(color)
    }

    fun convertColor(color: Int): Int {
        return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, color)))
    }
}