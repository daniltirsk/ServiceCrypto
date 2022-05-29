package com.example.servicedemo

import android.app.Notification.VISIBILITY_PUBLIC
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    var currencyToTrack: Int = 0
    var is_running = false
    lateinit var list: ListView
//    lateinit var listItems: MutableList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.spinner)
        var spinnerItems = mutableListOf<String>("BTC","ETH","USDT","BNB","DOGE")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, spinnerItems)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                currencyToTrack = position
                crypto = spinnerItems[currencyToTrack]
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        var button = findViewById<Button>(R.id.btnSubscribeToRate)

        button.setOnClickListener(){
            onClick(it)
        }

        list = findViewById(R.id.list)
//        listItems = mutableListOf<String>("EMPTY")
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, history)
        list.adapter = listAdapter





//        val CHANNEL_ID = "CHANNEL_ID"
//        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_DEFAULT)
//
//
//        } else {
//            TODO("VERSION.SDK_INT < O")
//        }
//
//        channel. description = "channelDescription"
//        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
//            .createNotificationChannel(channel)
//
//
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.cat)
//            .setContentTitle("My title")
//            .setContentText("My content for this notification")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//
//
//        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(LongArray(0))
//        with(NotificationManagerCompat.from(this)) {
//            notify(7, builder.build())
//        }
    }

    fun onClick(view: View){
        if (!is_running){
            intent = Intent(this, RateCheckService::class.java)
            startService(intent)
            is_running = true
        } else {
            intent = Intent(this, RateCheckService::class.java)
            startService(intent)
            history.clear()
            history.add("EMPTY")
            listAdapter.notifyDataSetChanged();
        }
    }

    companion object{
        var history = mutableListOf<String>("EMPTY")
        var crypto = "BTC"
        var lastPrice = 0.0
        lateinit var listAdapter: ArrayAdapter<String>
    }
}