package com.example.myvoozkotlin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.myvoozkotlin.helpers.Constants
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp: Application() {


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
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(!it.isSuccessful){
                return@addOnCompleteListener
            }

            val token = it.result
            Log.e("FCMToken", "Token -> $token")
        }
    }
}