package com.wreckingball.magicdex.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.wreckingball.magicdex.R
import com.wreckingball.magicdex.models.Card
import kotlinx.android.synthetic.main.fragment_about.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class AboutFragment : Fragment(R.layout.fragment_about) {
    private val model: DashboardViewModel by sharedViewModel()

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val card = model.card
        populateData(view, card)
    }

    private fun populateData(view: View, card: Card) {
        populateMana(view.layoutMana, card.manaCost)
        view.textCmc.text = card.cmc.toString()
        val power = card.power
        val toughness = card.toughness
        if ((power != null && power.isNotEmpty()) || (toughness != null && toughness.isNotEmpty())) {
            view.textPower.text = card.power
            view.textToughness.text = card.toughness
        } else {
            view.cardView.visibility = View.GONE
        }
        view.textCardText.text = model.addImagesToText(context!!, card.text)
        if (!card.types.isNullOrEmpty()) {
            view.textTypes.text = getStringFromList(card.types)
        } else {
            view.typesBlock.visibility = View.GONE
        }
        if (!card.superTypes.isNullOrEmpty()) {
            view.textSuperTypes.text = getStringFromList(card.superTypes)
        } else {
            view.superTypesBlock.visibility = View.GONE
        }
        if (!card.subTypes.isNullOrEmpty()) {
            view.textSubTypes.text = getStringFromList(card.subTypes)
        } else {
            view.subTypesBlock.visibility = View.GONE
        }
        view.textRarity.text = card.rarity
        view.textSet.text = card.setName
    }

    private fun getStringFromList(strings: List<String>?) : String {
        var value = ""
        if (strings != null && strings.isNotEmpty()) {
            strings.map {
                value += "$it, "
            }
            value = value.substringBeforeLast(",")
        }
        return value
    }

    private fun populateMana(layout: LinearLayout, manaCost: String?) {
        manaCost?.let {
            val manaList = it.split("}")
            for (mana in manaList) {
                var cleanMana = mana.replace("{", "")
                cleanMana = cleanMana.replace("/", "")
                if (cleanMana.isNotEmpty()) {
                    layout.addView(model.getManaImage(context!!, cleanMana.toLowerCase(Locale.US)))
                }
            }
        }
    }
}