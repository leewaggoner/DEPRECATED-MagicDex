package com.wreckingball.magicdex.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.databinding.FragmentAboutBinding
import com.wreckingball.magicdex.utils.MagicUtil
import kotlinx.android.synthetic.main.fragment_about.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class AboutFragment : Fragment(R.layout.fragment_about) {
    private val model: DashboardViewModel by sharedViewModel()

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAboutBinding>(inflater, R.layout.fragment_about, container, false)
        binding.card = model.card
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addMana(view.layoutMana)
    }

    private fun addMana(layout: LinearLayout) {
        val manaList = model.getManaList()
        for (mana in manaList) {
            val cleanMana = model.getManaName(mana)
            if (cleanMana.isNotEmpty()) {
                layout.addView(MagicUtil.getManaImage(requireContext(), cleanMana.toLowerCase(Locale.US)))
            }
        }
    }
}