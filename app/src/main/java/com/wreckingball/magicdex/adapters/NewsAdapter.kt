package com.wreckingball.magicdex.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.News
import kotlinx.android.synthetic.main.item_news.view.*


class NewsAdapter(
    private val list: List<News>,
    private val context: Context
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: News) {
            itemView.newsTitle.text = item.title
            itemView.newsDate.text = item.date
            val picasso = Picasso.get()
            if (item.imageUrl == null || item.imageUrl.isEmpty()) {
                itemView.newsImage.setImageResource(R.drawable.news1)
            } else {
                picasso.load(item.imageUrl)
                    .placeholder(R.drawable.news1)
                    .into(itemView.newsImage)
            }
            itemView.setOnClickListener {
                val uriUrl: Uri = Uri.parse(item.link)
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                itemView.context.startActivity(launchBrowser)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}