package com.example.myvoozkotlin.fcm

import android.content.Intent
import android.util.Log
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.helpers.Constants
import com.example.myvoozkotlin.helpers.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.JsonParser
import io.realm.Realm

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCMService::", "From: " + remoteMessage.from)
        if (remoteMessage.data.containsKey("cmd")) {
            val broadcastIntent = Intent(Constants.BROADCAST_ACTION_MESSAGE)
            broadcastIntent.putExtra("cmd", remoteMessage.data["cmd"])
            broadcastIntent.putExtra("title", remoteMessage.data["title"])
            broadcastIntent.putExtra("text", remoteMessage.data["body"])
            broadcastIntent.putExtra("methodName", "chat")
            applicationContext.startService(Intent(this, MainActivity::class.java))
            Log.d("FCMService::", "Push type " + remoteMessage.data["cmd"])

            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("methodName", "chat")
            NotificationHelper.sendAlertNotification(
                applicationContext,
                remoteMessage.data["title"]!!,
                remoteMessage.data["body"]!!,
                Intent(this, MainActivity::class.java),
                null
            )
            when (remoteMessage.data["cmd"]) {
                Constants.LEAVE_GROUP_OF_USER_NOTIFICATION_COMMAND -> {
                    applicationContext.startService(Intent(this, MainActivity::class.java))
                    val dbUtils = DbUtils(Realm.getDefaultInstance())
                    val authUserModel = dbUtils.getCurrentAuthUser()
                    if(authUserModel != null){
                        authUserModel.idGroupOfUser = 0
                        dbUtils.setCurrentAuthUser(authUserModel)
                        NotificationHelper.sendAlertNotification(
                            this,
                            remoteMessage.data["title"]!!,
                            remoteMessage.data["body"]!!,
                            intent,
                            null
                        )
                    }
                }
                Constants.MAKE_THE_HEAD_USER_NOTIFICATION_COMMAND -> {
                    applicationContext.startService(Intent(this, MainActivity::class.java))
                    val dbUtils = DbUtils(Realm.getDefaultInstance())
                    val authUserModel = dbUtils.getCurrentAuthUser()
                    if(authUserModel != null){
                        if(authUserModel.groupOfUser != null){
                            authUserModel.groupOfUser!!.idOlder = authUserModel.id
                            authUserModel.groupOfUser!!.name = authUserModel.lastName + " " + authUserModel.firstName
                        }
                        dbUtils.setCurrentAuthUser(authUserModel)
                        NotificationHelper.sendAlertNotification(
                            this,
                            remoteMessage.data["title"]!!,
                            remoteMessage.data["body"]!!,
                            intent,
                            null
                        )
                    }
                }
                else -> {

                }
            }

            if (remoteMessage.data["cmd"] != "feedback_rating" && remoteMessage.data["cmd"] != "nps_notify_commands") {
                Log.d("FCMService::", "Send Broadcast " + broadcastIntent.extras)
                sendBroadcast(broadcastIntent)
            }
        }
    }
}