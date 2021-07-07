package com.example.myvoozkotlin.helpers.helpers

import android.util.EventLog
import androidx.lifecycle.MutableLiveData

interface ErrorHandlerRepository {
    fun handleErrorMessage(errorMessage: String): String
}