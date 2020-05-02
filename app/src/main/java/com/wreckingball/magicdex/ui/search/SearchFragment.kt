package com.wreckingball.magicdex.ui.search

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.CardPageAdapter
import com.wreckingball.magicdex.network.ERROR
import com.wreckingball.magicdex.network.LOADING
import com.wreckingball.magicdex.network.SUCCESS
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val args: SearchFragmentArgs by navArgs()
    private val model: SearchViewModel by viewModel { parametersOf(args.searchText) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var spanCount = 1
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 2
        }
        val layoutManager = GridLayoutManager(context, spanCount)

        recyclerViewSearch.layoutManager = layoutManager
        recyclerViewSearch.adapter = CardPageAdapter()

        model.cardList.observe(viewLifecycleOwner, Observer { pagedList ->
            if (pagedList.isEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.no_results_title)
                    .setMessage(getString(R.string.no_results).format(args.searchText))
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        requireActivity().onBackPressed()
                    }
                    .create()
                    .show()
            } else {
                val adapter = recyclerViewSearch.adapter as CardPageAdapter
                adapter.submitList(pagedList)
            }
        })

        model.networkStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status.status) {
                LOADING -> {
                    progressBarSearch.visibility = View.VISIBLE
                }
                SUCCESS -> {
                    progressBarSearch.visibility = View.INVISIBLE
                }
                ERROR -> {
                    progressBarSearch.visibility = View.INVISIBLE
                    Snackbar.make(view, status.message, Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY") {
                            progressBarSearch.visibility = View.VISIBLE
                            status.retryable.retry()
                        }.show()
                }
            }
        })
    }
}
