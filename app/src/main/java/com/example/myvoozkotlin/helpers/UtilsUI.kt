package com.example.myvoozkotlin.helpers

import android.widget.Toast
import com.example.myvoozkotlin.BaseApp

object UtilsUI {
    fun MakeToast(resId: Int?) {
        Toast.makeText(BaseApp.instance, resId!!, Toast.LENGTH_SHORT).show()
    }

    fun MakeToast(text: String) {
        Toast.makeText(BaseApp.instance, text, Toast.LENGTH_SHORT).show()
    }
}