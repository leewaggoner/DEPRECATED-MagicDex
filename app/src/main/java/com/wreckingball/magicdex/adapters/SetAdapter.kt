package com.wreckingball.magicdex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.databinding.ItemSetsBinding
import com.wreckingball.magicdex.models.Set


class SetAdapter(
    private val list: List<Set>
) : RecyclerView.Adapter<SetAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemSetsBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Set) {
            binding.set = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val setsBinding = ItemSetsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(setsBinding, setsBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}