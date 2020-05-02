package com.wreckingball.magicdex.ui.sets

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.SetAdapter
import com.wreckingball.magicdex.network.ERROR
import com.wreckingball.magicdex.network.LOADING
import com.wreckingball.magicdex.network.SUCCESS
import kotlinx.android.synthetic.main.fragment_sets.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetsFragment : Fragment(R.layout.fragment_sets) {
    private val model : SetsViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.getSets()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.sets.observe(viewLifecycleOwner, Observer { sets ->
            when (sets.status) {
                LOADING -> {
                    progressBarSets.visibility = View.VISIBLE
                }
                SUCCESS -> {
                    progressBarSets.visibility = View.INVISIBLE
                    recyclerViewSets.adapter = SetAdapter(sets.sets)
                }
                ERROR -> {
                    progressBarSets.visibility = View.INVISIBLE
                    Snackbar.make(view, sets.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}