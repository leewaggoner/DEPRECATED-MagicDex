package com.wreckingball.magicdex.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.MenuAdapter
import com.wreckingball.magicdex.adapters.NewsAdapter
import com.wreckingball.magicdex.models.Menu
import com.wreckingball.magicdex.models.News
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val recyclerViewMenu = root.recyclerViewMenu

        recyclerViewMenu.layoutManager = GridLayoutManager(context, 2)
        homeViewModel.getListMenu(context!!).observe(this, Observer {
            val items: List<Menu> = it
            recyclerViewMenu.adapter = MenuAdapter(items, root.context)
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar.visibility = View.VISIBLE

        val recyclerViewNews = view.recyclerViewNews

        recyclerViewNews.layoutManager = GridLayoutManager(context, 1)
        recyclerViewNews.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        homeViewModel.getListNews().observe(this, Observer {
            progressBar.visibility = View.INVISIBLE
            recyclerViewNews.adapter = NewsAdapter(it, view.context)
        })
    }
}
