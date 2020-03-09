package com.wreckingball.magicdex.adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.ui.magicdex.MagicDexFragmentDirections
import com.wreckingball.magicdex.utils.MagicUtil
import kotlinx.android.synthetic.main.item_card.view.*

class CardPageAdapter : PagedListAdapter<Card, CardPageAdapter.ViewHolder>(Card.Diff.DIFF_ITEMS) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Card) {
            itemView.textViewCardName.text = item.name
            itemView.textViewId.text = item.number

            val color = MagicUtil.getCardColor(itemView.context, item.colorIdentity)
            itemView.magicdexLayoutBackground.background.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP )

            item.types?.elementAtOrNull(0).let {
                itemView.textViewType.text = it
                if (it == null) {
                    itemView.textViewType.visibility = View.GONE
                }
            }

            itemView.textViewRarity.text = item.rarity

            val imageUrl = MagicUtil.makeHttps(item.imageUrl)

            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(android.R.color.transparent)
                .into(itemView.imageViewCard)

            itemView.setOnClickListener {
                val action = MagicDexFragmentDirections.actionMagicDexFragmentToDashboardFragment(item)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = getItem(position)
        card?.let { holder.bindView(it) }
    }
}