package com.wreckingball.magicdex.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.utils.MagicUtil
import kotlinx.android.synthetic.main.fragment_card_text.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CardTextFragment : Fragment(R.layout.fragment_card_text) {
    val model: DashboardViewModel by sharedViewModel()

    companion object {
        fun newInstance() = CardTextFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val card = model.card
        view.textCurrent.text = card.text
        if (!card.flavor.isNullOrEmpty()) {
            view.textFlavorHeader.visibility = View.VISIBLE
            view.textFlavor.visibility = View.VISIBLE
            view.textFlavor.text = card.flavor
        }
        view.textOriginal.text = MagicUtil.addImagesToText(context!!, card.originalText)
    }
}