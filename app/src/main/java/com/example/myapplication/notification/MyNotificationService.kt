package com.example.myapplication.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class MyNotificationService(private val context: Context){
    val CHANNEL_ID = "channelID1"
    val CHANNEL_NAME = "channelName1"
    val NOTIF_ID = 1
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = TaskStackBuilder.create(context).run{
        addNextIntentWithParentStack(intent)
        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    val notif = NotificationCompat.Builder(context,CHANNEL_ID)
        .setContentTitle("Congrats")
        .setContentText("You have fetched new 10 Images click here to Fetch More!!!!")
        .setSmallIcon(R.drawable.baseline_notifications_active_24)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .build()
    val notiManger = NotificationManagerCompat.from(context)
    private fun createNotifChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
    init{
        createNotifChannel(context)
    }
    fun createNotification(){
        if (ActivityCompat.checkSelfPermission(
                this.context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            this.notiManger.notify(NOTIF_ID,notif)
        }

        //notiManger.notify(NOTIF_ID,notif)
    }
}