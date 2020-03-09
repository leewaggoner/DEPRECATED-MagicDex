package com.wreckingball.magicdex.ui.magicdex

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.CardPageAdapter
import com.wreckingball.magicdex.models.Card
import com.wreckingball.magicdex.network.ERROR
import com.wreckingball.magicdex.network.LOADING
import com.wreckingball.magicdex.network.SUCCESS
import kotlinx.android.synthetic.main.fragment_magic_dex.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MagicDexFragment : Fragment(R.layout.fragment_magic_dex) {
    private val model: MagicDexViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var spanCount = 1
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 2
        }
        val layoutManager = GridLayoutManager(context, spanCount)

        recyclerViewDex.layoutManager = layoutManager
        recyclerViewDex.adapter = CardPageAdapter()

        model.cardList.observe(viewLifecycleOwner, Observer<PagedList<Card>> { pagedList ->
            val adapter = recyclerViewDex.adapter as CardPageAdapter
            adapter.submitList(pagedList)
        })

        model.networkStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status.status) {
                LOADING -> {
                    progressBarDex.visibility = View.VISIBLE
                }
                SUCCESS -> {
                    progressBarDex.visibility = View.INVISIBLE
                }
                ERROR -> {
                    progressBarDex.visibility = View.INVISIBLE
                    Snackbar.make(view, status.message, Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY") {
                            progressBarDex.visibility = View.VISIBLE
                            status.retryable.retry()
                        }.show()
                }
            }
        })
    }
}
