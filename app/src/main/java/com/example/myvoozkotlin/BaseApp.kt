package com.example.myvoozkotlin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.myvoozkotlin.domain.framework.Interactors
import com.example.myvoozkotlin.helpers.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp: Application() {

    lateinit var interactors: Interactors

    companion object{
        var instance: BaseApp? = null
        private var sharedPreferences: SharedPreferences? = null

        @JvmStatic
        fun getSharedPref(): SharedPreferences {
            if (sharedPreferences == null)
                sharedPreferences = instance?.getSharedPreferences(
                    "ru.ufanet.myvooz_preferences",
                    Context.MODE_PRIVATE
                )
            return sharedPreferences!!
        }

        fun getAuthState(): Int{
            return getSharedPref().getInt(Constants.APP_PREFERENCES_AUTH_STATE, 0)
        }

        fun setAuthState(state: Int){
            getSharedPref().edit().putInt(
                Constants.APP_PREFERENCES_AUTH_STATE,
                state
            ).apply()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}