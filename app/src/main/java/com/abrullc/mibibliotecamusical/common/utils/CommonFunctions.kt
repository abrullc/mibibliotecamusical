package com.abrullc.mibibliotecamusical.common.utils

import android.content.Context
import android.webkit.URLUtil
import com.abrullc.mibibliotecamusical.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    fun getAnyo(date: Date): String {
        val fecha = date.toString().split(" ")
        val fechaAnyo = fecha[fecha.lastIndex]

        return fechaAnyo
    }

    fun validateURL(url: String): Boolean {
        if (!URLUtil.isValidUrl(url.trim()) && !url.trim().isEmpty()) {
            return false
        }

        return true
    }

    fun errorAlertDialog(texto: String, context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.dialog_error_title)
            .setMessage(texto)
            .setPositiveButton(R.string.dialog_confirm, null)
            .setCancelable(false)
            .show()
    }
}