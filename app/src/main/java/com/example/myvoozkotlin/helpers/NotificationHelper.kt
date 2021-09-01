package com.example.myvoozkotlin.helpers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.R
import java.util.*

object NotificationHelper {
    fun sendAlertNotification(
        context: Context,
        messageTitle: String,
        messageBody: String,
        intent: Intent?,
        url: String?
    ) {
        try {
            val contentIntent = PendingIntent.getActivity(
                context, 23222, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notifOreo(
                    context,
                    System.currentTimeMillis().toInt() / 10000,
                    messageTitle,
                    messageBody,
                    contentIntent
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notifLolli(
                    context,
                    System.currentTimeMillis().toInt() / 10000,
                    messageTitle,
                    messageBody,
                    contentIntent
                )
            } else {
                notifLegacy(
                    context,
                    System.currentTimeMillis().toInt() / 10000,
                    messageTitle,
                    messageBody,
                    contentIntent
                )
            }
        } catch (e: Exception) {
            Log.e("alert pushh err", "1")
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun notifOreo(
        context: Context,
        idd: Int,
        title: String,
        body: String,
        intent: PendingIntent
    ) {
        val mNM = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notif: Notification
        if (checkNightTimeNotif()) {
            mNM.deleteNotificationChannel(Constants.NIGHT_NOTIFICATION_CHANNEL)
            val notificationChannel = NotificationChannel(
                Constants.NIGHT_NOTIFICATION_CHANNEL,
                Constants.NIGHT_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = body
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(false)
            notificationChannel.setSound(null, null)
            mNM.createNotificationChannel(notificationChannel)
            notif = Notification.Builder(context, Constants.NIGHT_NOTIFICATION_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notif)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setStyle(Notification.BigTextStyle().bigText(body))
                .setSound(null)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .build()
        } else {
            mNM.deleteNotificationChannel(Constants.NORMAL_NOTIFICATION_CHANNEL)
            val mChannel = NotificationChannel(
                Constants.NORMAL_NOTIFICATION_CHANNEL,
                Constants.NORMAL_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            mChannel.description = body
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableLights(true)
            mNM.createNotificationChannel(mChannel)
            notif = Notification.Builder(context, Constants.NORMAL_NOTIFICATION_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notif)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setDefaults(
                    Notification.DEFAULT_SOUND
                            or Notification.DEFAULT_VIBRATE
                )
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(Notification.BigTextStyle().bigText(body))
                .build()
        }
        mNM.notify(idd, notif)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun notifLolli(
        context: Context,
        idd: Int,
        title: String,
        body: String,
        intent: PendingIntent
    ) {
        val mNM = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notif: Notification
        if (checkNightTimeNotif()) {
            notif = Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notif)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setSound(null)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setSound(null, null)
                .setLights(
                    Color.RED,
                    500,
                    500
                )
                .setStyle(Notification.BigTextStyle().bigText(body))
                .build()
        } else {
            notif = Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notif)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setDefaults(
                    (Notification.DEFAULT_SOUND
                            or Notification.DEFAULT_VIBRATE)
                )
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setLights(
                    Color.RED,
                    500,
                    500
                )
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(Notification.BigTextStyle().bigText(body))
                .build()
        }
        mNM.notify(idd, notif)
    }

    private fun notifLegacy(
        context: Context,
        id: Int,
        title: String,
        body: String,
        intent: PendingIntent
    ) {
        val mNM = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notif: Notification
        if (checkNightTimeNotif()) {
            notif = Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notif)
                .setAutoCancel(true)
                .setSound(null)
                .setContentIntent(intent)
                .setVibrate(longArrayOf(500, 500, 500, 500, 500))
                .setWhen(System.currentTimeMillis())
                .build()
        } else {
            notif = Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notif)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
                .setVibrate(longArrayOf(500, 500, 500, 500, 500))
                .setWhen(System.currentTimeMillis())
                .build()
        }
        mNM.notify(id, notif)
    }

    private fun checkNightTimeNotif(): Boolean {
        return if (BaseApp.getSharedPref().getBoolean(Constants._NIGHT_PUSH_NOTIFICATION_STATE, false)) {
            checkTime()
        } else {
            false
        }
    }

    private fun checkTime(): Boolean {
        val currentCalendar = Calendar.getInstance()
        val currentHour = currentCalendar[Calendar.HOUR_OF_DAY]
        return currentHour <= 8 || currentHour >= 22
    }
}