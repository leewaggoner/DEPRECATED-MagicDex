package com.wreckingball.magicdex.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.MenuAdapter
import com.wreckingball.magicdex.adapters.NewsAdapter
import com.wreckingball.magicdex.network.ERROR
import com.wreckingball.magicdex.network.LOADING
import com.wreckingball.magicdex.network.SUCCESS
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val model : HomeViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.getNewsList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return super.onCreateView(layoutInflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerViewNews = view.recyclerViewNews
        val recyclerViewMenu = view.recyclerViewMenu

        recyclerViewMenu.layoutManager = GridLayoutManager(context, 2)
        model.getMenuList(context!!).observe(viewLifecycleOwner, Observer { menuList ->
            recyclerViewMenu.adapter = MenuAdapter(menuList, view.context)
        })

        recyclerViewNews.layoutManager = GridLayoutManager(context, 1)
        recyclerViewNews.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        model.newsList.observe(viewLifecycleOwner, Observer { newsList ->
            when (newsList.status) {
                LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE
                    recyclerViewNews.adapter = NewsAdapter(newsList, view.context)
                }
                ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    Snackbar.make(view, newsList.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
