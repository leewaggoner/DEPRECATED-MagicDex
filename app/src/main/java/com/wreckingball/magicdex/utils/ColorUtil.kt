package com.wreckingball.magicdex.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat

class ColorUtil(val context: Context) {
    fun covertColor(color: Int): Int {
        return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, color)))
    }
}