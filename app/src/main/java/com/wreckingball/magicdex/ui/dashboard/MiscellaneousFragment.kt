package com.wreckingball.magicdex.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wreckingball.magicdex.R
import kotlinx.android.synthetic.main.fragment_miscellaneous.*
import kotlinx.android.synthetic.main.fragment_miscellaneous.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MiscellaneousFragment : Fragment(R.layout.fragment_miscellaneous) {
    private val model: DashboardViewModel by sharedViewModel()

    companion object {
        fun newInstance() = MiscellaneousFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val card = model.card
        val rulingBlock = view.rulingBlock
        val rulings = card.rulings
        if (rulings != null && rulings.isNotEmpty()) {
            for (ruling in rulings) {
                val layoutRuling = model.buildLayouts(context!!, ruling.date.toString(), ruling.text.toString(), true)
                rulingBlock.addView(layoutRuling)
            }
        } else {
            view.textRulings.visibility = View.GONE
            rulingBlock.visibility = View.GONE
        }
        val legalities = card.legalities
        if (legalities != null && legalities.isNotEmpty()) {
            for (legality in legalities) {
                val layoutRuling = model.buildLayouts(context!!, legality.format.toString(), legality.legality.toString(), false)
                legalityBlock.addView(layoutRuling)
            }
        } else {
            view.textLegalities.visibility = View.GONE
            legalityBlock.visibility = View.GONE
        }
    }
}