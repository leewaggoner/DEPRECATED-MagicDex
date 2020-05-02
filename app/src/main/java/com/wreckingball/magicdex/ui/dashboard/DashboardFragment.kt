package com.wreckingball.magicdex.ui.dashboard

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.ViewPagerAdapter
import com.wreckingball.magicdex.databinding.FragmentDashboardBinding
import com.wreckingball.magicdex.utils.MagicUtil
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashboardFragment : Fragment() {
    private val model: DashboardViewModel by sharedViewModel()
    private val args: DashboardFragmentArgs by navArgs()

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var aboutFragment: AboutFragment
    private lateinit var artFragment: ArtFragment
    private lateinit var cardTextFragment: CardTextFragment
    private lateinit var miscellaneousFragment: MiscellaneousFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.card = args.cardArg

        adapter = ViewPagerAdapter(childFragmentManager)
        aboutFragment = AboutFragment.newInstance()
        artFragment = ArtFragment.newInstance()
        cardTextFragment = CardTextFragment.newInstance()
        miscellaneousFragment = MiscellaneousFragment.newInstance()
        adapter.addFragment(aboutFragment, getString(R.string.tab_about))
        adapter.addFragment(artFragment, getString(R.string.tab_art))
        adapter.addFragment(cardTextFragment, getString(R.string.tab_card_text))
        adapter.addFragment(miscellaneousFragment, getString(R.string.tab_miscellaneous))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(inflater, R.layout.fragment_dashboard, container, false)
        binding.card = model.card
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewPager.adapter = adapter
        view.tabs.setupWithViewPager(view.viewPager)

        val color = MagicUtil.getCardColor(view.context, model.card.colorIdentity)
        view.app_bar.background.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP )
        view.toolbar_layout.contentScrim?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}
