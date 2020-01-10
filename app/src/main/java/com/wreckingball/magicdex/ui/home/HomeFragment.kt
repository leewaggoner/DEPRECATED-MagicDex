package com.wreckingball.magicdex.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val recyclerViewMenu = root.recyclerViewMenu
        val recyclerViewNews = root.recyclerViewNews

        recyclerViewMenu.layoutManager = GridLayoutManager(context, 2)
        recyclerViewNews.layoutManager = GridLayoutManager(context, 1)
        recyclerViewNews.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        homeViewModel.getListMenu(context!!).observe(this, Observer {
            val items: List<Menu> = it
            recyclerViewMenu.adapter = MenuAdapter(items, root.context)
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar.visibility = View.VISIBLE

        homeViewModel.getListNews().observe(this, Observer {
            progressBar.visibility = View.INVISIBLE
            var items: List<News> = it
            if (items.size > 20) {
                items = items.subList(0, 20)
            }
            recyclerViewNews.adapter = NewsAdapter(items, view.context)
        })
    }
}
