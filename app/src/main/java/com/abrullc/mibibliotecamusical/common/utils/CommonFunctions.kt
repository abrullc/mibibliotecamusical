package com.abrullc.mibibliotecamusical.common.utils

import java.util.Date

class CommonFunctions {
    fun parseDate(date: Date): String {
        val fecha = date.toString().split(" ")
        val fechaDia = fecha[2]
        val fechaMes = fecha[1]
        val fechaAnyo = fecha[fecha.lastIndex]
        val formatedFecha = "$fechaDia - $fechaMes - $fechaAnyo"

        return formatedFecha
    }
}