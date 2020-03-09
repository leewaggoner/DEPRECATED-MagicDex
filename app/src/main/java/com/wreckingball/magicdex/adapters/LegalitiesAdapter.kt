package com.wreckingball.magicdex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.Legalities
import kotlinx.android.synthetic.main.item_legalities.view.*

class LegalitiesAdapter(
    private val list: List<Legalities>,
    private val context: Context
) : RecyclerView.Adapter<LegalitiesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Legalities) {
            itemView.legalityFormat.text = item.format
            itemView.legalityText.text = item.legality
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_legalities, parent, false)
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