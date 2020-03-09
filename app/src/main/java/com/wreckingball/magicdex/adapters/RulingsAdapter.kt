package com.wreckingball.magicdex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.Rulings
import com.wreckingball.magicdex.utils.MagicUtil
import kotlinx.android.synthetic.main.item_rulings.view.*

class RulingsAdapter(
    private val list: List<Rulings>,
    private val context: Context
) : RecyclerView.Adapter<RulingsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Rulings) {
            itemView.rulingDate.text = item.date
            itemView.rulingText.text = MagicUtil.addImagesToText(itemView.context, item.text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rulings, parent, false)
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