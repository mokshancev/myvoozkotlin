package com.example.myvoozkotlin.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FCMInstance : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.e("fcmToken", "Refreshed token: $token")
    }
}