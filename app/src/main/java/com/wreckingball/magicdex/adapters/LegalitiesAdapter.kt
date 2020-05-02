package com.wreckingball.magicdex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.databinding.ItemLegalitiesBinding
import com.wreckingball.magicdex.models.Legalities

class LegalitiesAdapter(
    private val list: List<Legalities>
) : RecyclerView.Adapter<LegalitiesAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemLegalitiesBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Legalities) {
            binding.legality = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val legalitiesBinding = ItemLegalitiesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(legalitiesBinding, legalitiesBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}