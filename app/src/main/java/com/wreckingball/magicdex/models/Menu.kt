package com.wreckingball.magicdex.models

import com.wreckingball.magicdex.ui.home.HomeViewModel.Companion.MenuId

data class Menu(
    val id: MenuId,
    val name: String,
    val color: Int
)