package com.wreckingball.magicdex.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.Menu
import com.wreckingball.magicdex.ui.home.HomeFragmentDirections
import com.wreckingball.magicdex.ui.home.HomeViewModel.Companion.MenuId
import com.wreckingball.magicdex.utils.MagicUtil
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter(
    private val list: List<Menu>,
    private val context: Context) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Menu) {
            itemView.textViewName.text = item.name

            val color = MagicUtil.convertColor(itemView.context, item.color)
            itemView.relativeLayoutBackground.background.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)

            itemView.setOnClickListener {view ->
                when (item.id) {
                    MenuId.MAGIC_DEX ->
                        gotoScreen(view, HomeFragmentDirections.actionHomeFragmentToMagicDexFragment())
                    MenuId.SETS ->
                        gotoScreen(view, HomeFragmentDirections.actionHomeFragmentToSetsFragment())
                    MenuId.TYPES ->
                        gotoScreen(view, HomeFragmentDirections.actionHomeFragmentToTypesFragment())
                    MenuId.FORMATS ->
                        gotoScreen(view, HomeFragmentDirections.actionHomeFragmentToMagicDexFragment())
                }
            }
        }

        private fun gotoScreen(view: View, action: NavDirections) {
            view.findNavController().navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
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