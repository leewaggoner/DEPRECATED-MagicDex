package com.wreckingball.magicdex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.databinding.ItemTypesBinding

class TypeAdapter(
    private val list: List<String>
) : RecyclerView.Adapter<TypeAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemTypesBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: String) {
            binding.type = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val typesBinding = ItemTypesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(typesBinding, typesBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}