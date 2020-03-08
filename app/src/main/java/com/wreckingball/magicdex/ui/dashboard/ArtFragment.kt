package com.wreckingball.magicdex.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.utils.MagicUtil
import kotlinx.android.synthetic.main.fragment_art.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ArtFragment : Fragment(R.layout.fragment_art) {
    private val model: DashboardViewModel by sharedViewModel()

    companion object {
        fun newInstance() = ArtFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val card = model.card

        view.textArtist.text = "Artist: ${card.artist}"

        val imageUrl = MagicUtil().makeHttps(card.imageUrl)
        Glide.with(context!!)
            .load(imageUrl)
            .placeholder(android.R.color.transparent)
            .into(view.imageArt)
    }
}