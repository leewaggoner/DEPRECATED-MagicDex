package com.wreckingball.magicdex.ui.dashboard

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.ViewPagerAdapter
import com.wreckingball.magicdex.utils.MagicUtil
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val model: DashboardViewModel by sharedViewModel()
    private val args: DashboardFragmentArgs by navArgs()

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var aboutFragment: AboutFragment
    private lateinit var artFragment: ArtFragment
    private lateinit var cardTextFragment: CardTextFragment
    private lateinit var variationsFragment: VariationsFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.card = args.cardArg

        adapter = ViewPagerAdapter(childFragmentManager)
        aboutFragment = AboutFragment.newInstance()
        artFragment = ArtFragment.newInstance()
        cardTextFragment = CardTextFragment.newInstance()
        variationsFragment = VariationsFragment.newInstance()
        adapter.addFragment(aboutFragment, getString(R.string.tab_about))
        adapter.addFragment(artFragment, getString(R.string.tab_art))
        adapter.addFragment(cardTextFragment, getString(R.string.tab_card_text))
        adapter.addFragment(variationsFragment, getString(R.string.tab_variations))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewPager.adapter = adapter
        view.tabs.setupWithViewPager(view.viewPager)

        val card = model.card
        val color = MagicUtil().getCardColor(view.context, card.colorIdentity)
        view.app_bar.background.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP )
        view.toolbar_layout.contentScrim?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        cardName.text = card.name
        cardId.text = card.number

        card.types?.elementAtOrNull(0).let {
            view.textViewType3.text = it
            if (it == null) {
                view.textViewType3.visibility = View.GONE
            }
        }

        card.types?.elementAtOrNull(1).let {
            view.textViewType2.text = it
            if (it == null) {
                view.textViewType2.visibility = View.GONE
            }
        }

        card.types?.elementAtOrNull(2).let {
            view.textViewType1.text = it
            if (it == null) {
                view.textViewType1.visibility = View.GONE
            }
        }

        val imageUrl = MagicUtil().makeHttps(card.imageUrl)
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(android.R.color.transparent)
            .into(view.cardImage)
    }
}
