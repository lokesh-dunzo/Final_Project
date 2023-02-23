package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build

class Notification {
    companion object{
        val CHANNEL_ID = "channelID"
        val CHANNEL_NAME = "channelName"
        val NOTIF_ID = 0
    }
    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            //val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            //manager.createNotificationChannel(channel)
        }
    }
}