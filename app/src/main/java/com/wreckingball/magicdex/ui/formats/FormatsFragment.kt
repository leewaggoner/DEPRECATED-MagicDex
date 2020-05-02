package com.wreckingball.magicdex.ui.formats

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.adapters.FormatsAdapter
import com.wreckingball.magicdex.network.ERROR
import com.wreckingball.magicdex.network.LOADING
import com.wreckingball.magicdex.network.SUCCESS
import kotlinx.android.synthetic.main.fragment_formats.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class FormatsFragment : Fragment(R.layout.fragment_formats) {
    private val model : FormatsViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.getFormats()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.formats.observe(viewLifecycleOwner, Observer { formats ->
            when (formats.status) {
                LOADING -> {
                    progressBarFormats.visibility = View.VISIBLE
                }
                SUCCESS -> {
                    progressBarFormats.visibility = View.INVISIBLE
                    recyclerViewFormats.adapter = FormatsAdapter(formats.formats)
                }
                ERROR -> {
                    progressBarFormats.visibility = View.INVISIBLE
                    Snackbar.make(view, formats.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        formatsMoreInfo.setOnClickListener {
            model.handleFormatsClick(requireContext())
        }
    }
}
