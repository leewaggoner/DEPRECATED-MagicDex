package com.wreckingball.magicdex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.databinding.ItemFormatsBinding

class FormatsAdapter(
    private val list: List<String>
) : RecyclerView.Adapter<FormatsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemFormatsBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: String) {
            binding.format = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val formatsBinding = ItemFormatsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(formatsBinding, formatsBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}