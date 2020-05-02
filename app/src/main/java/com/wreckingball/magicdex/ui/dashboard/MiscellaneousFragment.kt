package com.wreckingball.magicdex.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.LegalitiesAdapter
import com.wreckingball.magicdex.adapters.RulingsAdapter
import kotlinx.android.synthetic.main.fragment_miscellaneous.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MiscellaneousFragment : Fragment(R.layout.fragment_miscellaneous) {
    private val model: DashboardViewModel by sharedViewModel()

    companion object {
        fun newInstance() = MiscellaneousFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rulings = model.card.rulings
        if (!rulings.isNullOrEmpty()) {
            rulingsBlock.visibility = View.VISIBLE
            recyclerViewRulings.layoutManager = LinearLayoutManager(context)
            recyclerViewRulings.adapter = RulingsAdapter(rulings)
        }

        val legalities = model.card.legalities
        if(!legalities.isNullOrEmpty()) {
            legalitiesBlock.visibility = View.VISIBLE
            recyclerViewLegalities.layoutManager = LinearLayoutManager(context)
            recyclerViewLegalities.adapter = LegalitiesAdapter(legalities)
        }
    }
}