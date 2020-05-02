package com.wreckingball.magicdex.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.databinding.FragmentCardTextBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CardTextFragment : Fragment() {
    val model: DashboardViewModel by sharedViewModel()

    companion object {
        fun newInstance() = CardTextFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentCardTextBinding>(inflater, R.layout.fragment_card_text, container, false)
        binding.card = model.card
        return binding.root
    }
}