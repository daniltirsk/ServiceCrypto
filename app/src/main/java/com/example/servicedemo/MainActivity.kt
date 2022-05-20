package com.example.servicedemo

import android.app.Notification.VISIBILITY_PUBLIC
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val CHANNEL_ID = "CHANNEL_ID"
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_DEFAULT)


        } else {
            TODO("VERSION.SDK_INT < O")
        }
        
        channel. description = "channelDescription"
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.cat)
            .setContentTitle("My title")
            .setContentText("My content for this notification")
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(LongArray(0))
        with(NotificationManagerCompat.from(this)) {
            notify(7, builder.build())
        }
    }
}