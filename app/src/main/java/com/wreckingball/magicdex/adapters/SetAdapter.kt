package com.wreckingball.magicdex.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.Set
import kotlinx.android.synthetic.main.item_sets.view.*

class SetAdapter(
    private val list: List<Set>,
    private val context: Context
) : RecyclerView.Adapter<SetAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(context: Context, item: Set) {
            Log.e("SetAdapter", "bindView")
            itemView.textName.text = item.name
            itemView.textReleaseDate.text = context.getString(R.string.release_date, item.releaseDate)
            itemView.textBorder.text = item.border
            itemView.textType.text = context.getString(R.string.set_type, item.type)
            if (item.onlineOnly) {
                itemView.textOnlineOnly.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sets, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(context, item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}