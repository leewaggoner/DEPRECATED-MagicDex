package com.wreckingball.magicdex.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.wreckingball.magicdex.R

object Bindings {
    @BindingAdapter("bind:textWithImages")
    @JvmStatic
    fun loadTextWithImages(textView: TextView, text: String?) {
        textView.text = MagicUtil.addImagesToText(textView.context, text)
        textView.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    @BindingAdapter("bind:textOrGone")
    @JvmStatic
    fun loadTextOrGone(textView: TextView, text: String?) {
        textView.text = text
        textView.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    @BindingAdapter("bind:allTextFromList")
    @JvmStatic
    fun loadTextFromList(textView: TextView, text: List<String>?) {
        var value = ""
        if (!text.isNullOrEmpty()) {
            text.map {str ->
                value += "$str, "
            }
            value = value.substringBeforeLast(",")
            textView.text = value
        }
    }

    @BindingAdapter(value = ["bind:textFromList", "bind:textIndex"])
    @JvmStatic
    fun loadListText(textView: TextView, textList: List<String>?, textIndex: Int) {
        textList?.elementAtOrNull(textIndex).let {str ->
            textView.text = str
            textView.visibility = if (str.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    @BindingAdapter("bind:imageUrl")
    @JvmStatic
    fun loadCardImage(imageView: ImageView, imageUrl: String?) {
        val secureUrl = MagicUtil.makeHttps(imageUrl)
        Glide.with(imageView.context)
            .load(secureUrl)
            .placeholder(android.R.color.transparent)
            .into(imageView)
    }

    @BindingAdapter("bind:newsImageUrl")
    @JvmStatic
    fun loadNewsImage(imageView: ImageView, imageUrl: String?) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.news1)
            .error(R.drawable.news1)
            .into(imageView)
    }
}
