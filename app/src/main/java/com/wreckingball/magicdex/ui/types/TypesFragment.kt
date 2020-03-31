package com.wreckingball.magicdex.ui.types

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.TypeAdapter
import com.wreckingball.magicdex.network.ERROR
import com.wreckingball.magicdex.network.LOADING
import com.wreckingball.magicdex.network.SUCCESS
import kotlinx.android.synthetic.main.fragment_sets.progressBarSets
import kotlinx.android.synthetic.main.fragment_types.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class TypesFragment : Fragment(R.layout.fragment_types) {
    private val model : TypesViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.getSupertypes()
        model.getTypes()
        model.getSubtypes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.networkStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status.status) {
                LOADING -> {
                    progressBarSets.visibility = View.VISIBLE
                }
                SUCCESS -> {
                    progressBarSets.visibility = View.INVISIBLE
                }
                ERROR -> {
                    progressBarSets.visibility = View.INVISIBLE
                    Snackbar.make(view, status.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        recyclerViewSupertypes.layoutManager = GridLayoutManager(context, 2)
        model.supertypes.observe(viewLifecycleOwner, Observer { supertypes ->
            if (!supertypes.supertypes.isNullOrEmpty()) {
                recyclerViewSupertypes.adapter = TypeAdapter(supertypes.supertypes, view.context)
            }
        })

        recyclerViewTypes.layoutManager = GridLayoutManager(context, 2)
        model.types.observe(viewLifecycleOwner, Observer { types ->
            if (!types.types.isNullOrEmpty()) {
                recyclerViewTypes.adapter = TypeAdapter(types.types, view.context)
            }
        })

        recyclerViewSubtypes.layoutManager = GridLayoutManager(context, 2)
        model.subtypes.observe(viewLifecycleOwner, Observer { subtypes ->
            if (!subtypes.subtypes.isNullOrEmpty()) {
                recyclerViewSubtypes.adapter = TypeAdapter(subtypes.subtypes, view.context)
            }
        })

        supertypeMoreInfo.setOnClickListener {
            model.handleLinkClick(context!!, TypesViewModel.LinkType.SUPERTYPE)
        }

        typeMoreInfo.setOnClickListener {
            model.handleLinkClick(context!!, TypesViewModel.LinkType.TYPE)
        }

        subtypeMoreInfo.setOnClickListener {
            model.handleLinkClick(context!!, TypesViewModel.LinkType.SUBTYPE)
        }
    }
}
