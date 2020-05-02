package com.wreckingball.magicdex.adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.databinding.ItemCardBinding
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.ui.magicdex.MagicDexFragmentDirections
import com.wreckingball.magicdex.ui.search.SearchFragmentDirections
import com.wreckingball.magicdex.utils.MagicUtil
import kotlinx.android.synthetic.main.item_card.view.*

class CardPageAdapter : PagedListAdapter<Card, CardPageAdapter.ViewHolder>(Card.Diff.DIFF_ITEMS) {
    class ViewHolder(private val binding: ItemCardBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Card) {
            val color = MagicUtil.getCardColor(itemView.context, item.colorIdentity)
            itemView.magicdexLayoutBackground.background.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP )

            item.types?.elementAtOrNull(0).let {
                itemView.textViewType.text = it
                if (it == null) {
                    itemView.textViewType.visibility = View.GONE
                }
            }

            binding.card = item
            binding.executePendingBindings()

            itemView.setOnClickListener {
                try {
                    val action = MagicDexFragmentDirections.actionMagicDexFragmentToDashboardFragment(item)
                    it.findNavController().navigate(action)
                } catch (ex: Exception) {
                    val action = SearchFragmentDirections.actionSearchFragmentToDashboardFragment(item)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cardBinding = ItemCardBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(cardBinding, cardBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = getItem(position)
        card?.let { holder.bindView(it) }
    }
}