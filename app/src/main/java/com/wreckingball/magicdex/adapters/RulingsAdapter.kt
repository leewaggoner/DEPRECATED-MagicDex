package com.wreckingball.magicdex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.databinding.ItemRulingsBinding
import com.wreckingball.magicdex.models.Rulings

class RulingsAdapter(
    private val list: List<Rulings>
) : RecyclerView.Adapter<RulingsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemRulingsBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Rulings) {
            binding.ruling = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rulingsBinding = ItemRulingsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(rulingsBinding, rulingsBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}