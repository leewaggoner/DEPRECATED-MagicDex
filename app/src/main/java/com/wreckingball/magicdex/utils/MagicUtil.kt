package com.wreckingball.magicdex.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.wreckingball.magicdex.R
import java.util.*

class MagicUtil() {
    fun getCardColor(context: Context, colorIdentity: List<String>?) : Int {
        val type = colorIdentity?.getOrNull(0)
        val color = when (type?.toUpperCase(Locale.US)) {
            "G" -> R.color.lightGreen
            "U" -> R.color.lightBlue
            "B" -> R.color.lightBlack
            "R" -> R.color.lightRed
            "W" -> R.color.lightWhite
            else -> R.color.lightBrown
            //C - colorless
            //T - tap
            //Q - untap
        }
        return convertColor(context, color)
    }

    fun convertColor(context: Context, color: Int): Int {
        return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, color)))
    }

    fun makeHttps(url: String?) : String {
        var imageUrl = ""
        url?.let {
            imageUrl = url
            if (!it.startsWith("https:", true)) {
                imageUrl = it.substringAfter("http")
                imageUrl = "https$imageUrl"
            }
        }
        return imageUrl
    }

    fun dpToPixels(context: Context, dp: Int) : Int {
        return (dp * context.resources.displayMetrics.density).toInt();
    }
}