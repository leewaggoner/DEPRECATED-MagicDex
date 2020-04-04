package com.wreckingball.magicdex.ui.types

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.wreckingball.magicdex.repositories.TypesRepository


class TypesViewModel(private val typesRepository: TypesRepository) : ViewModel() {
    var networkStatus = typesRepository.networkStatus
    var supertypes = typesRepository.supertypes
    var types = typesRepository.types
    var subtypes = typesRepository.subtypes

    enum class LinkType {
        SUPERTYPE,
        TYPE,
        SUBTYPE
    }

    fun getSupertypes() {
        if (supertypes.value == null) {
            typesRepository.fetchSupertypes()
        }
    }

    fun getTypes() {
        if (types.value == null) {
            typesRepository.fetchTypes()
        }
    }

    fun getSubtypes() {
        if (subtypes.value == null) {
            typesRepository.fetchSubtypes()
        }
    }

    fun handleLinkClick(context: Context, linkType: LinkType) {
        when (linkType) {
            LinkType.SUPERTYPE -> {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://mtg.gamepedia.com/Supertype")))
            }
            LinkType.TYPE -> {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://mtg.gamepedia.com/Card_type")))
            }
            LinkType.SUBTYPE -> {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://mtg.gamepedia.com/Subtype")))
            }
        }
    }
}