package com.wreckingball.magicdex.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.databinding.ItemNewsBinding
import com.wreckingball.magicdex.models.News


class NewsAdapter(
    private val list: List<News>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemNewsBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: News) {
            binding.news = item
            binding.executePendingBindings()
            itemView.setOnClickListener {
                val uriUrl: Uri = Uri.parse(item.link)
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                itemView.context.startActivity(launchBrowser)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val newsBinding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(newsBinding, newsBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItem(index: Int) : News {
        return list[index]
    }
}