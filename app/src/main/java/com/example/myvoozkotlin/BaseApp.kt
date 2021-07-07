package com.example.myvoozkotlin

import android.app.Application
import com.example.myvoozkotlin.domain.framework.Interactors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp: Application() {

    lateinit var interactors: Interactors

    companion object{
        var instance: BaseApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}