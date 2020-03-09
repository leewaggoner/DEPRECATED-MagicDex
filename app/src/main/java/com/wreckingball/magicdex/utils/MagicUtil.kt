package com.wreckingball.magicdex.utils

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.wreckingball.magicdex.R
import java.util.*

object MagicUtil {
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
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    fun getManaImage(context: Context, mana: String) : ImageView {
        val imageSize = MagicUtil.dpToPixels(context, 16)
        var cleanMana = mana.replace("{", "")
        cleanMana = cleanMana.replace("/", "")
        val imageView = ImageView(context)
        if (cleanMana.isNotEmpty()) {
            imageView.layoutParams = LinearLayout.LayoutParams(imageSize, imageSize)
            imageView.setImageResource(MagicUtil.mapManaToResource(context, cleanMana.toLowerCase(Locale.US)))
        }
        return imageView
    }

    fun addImagesToText(context: Context, text: String?) : SpannableStringBuilder {
        val ssb = SpannableStringBuilder("")
        data class ImageMap(val start: Int, val end: Int, val imageId: Int)
        text?.let {
            ssb.append(text)
            val imageMap: MutableList<ImageMap> = mutableListOf()
            val imageSize = dpToPixels(context, 16)
            var start = it.indexOf("{")
            do {
                if (start != -1) {
                    val end = it.indexOf("}", start) + 1
                    var imageText = it.subSequence(start, end).toString()
                    imageText = imageText.replace("{", "")
                    imageText = imageText.replace("/", "")
                    imageText = imageText.replace("}", "")
                    val id = mapManaToResource(context, imageText.toLowerCase(Locale.US))
                    imageMap.add(ImageMap(start, end, id))
                    start = it.indexOf("{", end - 1)
                }
            } while (start != -1)
            for (image in imageMap) {
                val drawable = ContextCompat.getDrawable(context, image.imageId)
                drawable?.let {
                    drawable.setBounds(0, 0, imageSize, imageSize)
                    val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
                    ssb.setSpan(imageSpan, image.start, image.end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                }
            }
        }
        return ssb
    }

    fun mapManaToResource(context: Context, mana: String) : Int {
        var value = context.resources.getIdentifier("mana_$mana", "drawable", context.packageName)
        if (value == 0) {
            value = R.drawable.mana_unexpected
        }
        return value
    }
}