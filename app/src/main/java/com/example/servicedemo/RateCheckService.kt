package com.example.servicedemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.json.JSONObject
import java.net.URL
import java.net.UnknownHostException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RateCheckService : Service() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mHandler = Handler()
        mRunnable = Runnable {
            GlobalScope.launch(Dispatchers.IO) {
                loadCrypto()
            }
        }
        mHandler.postDelayed(mRunnable, 5000)
        return Service.START_STICKY
    }

    suspend fun loadCrypto() {
        val API_KEY = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c"
        val CRYPTO = MainActivity.crypto
        val URL = "https://min-api.cryptocompare.com/data/price?fsym=$CRYPTO&tsyms=USD&api_key=$API_KEY"
        try {
            val data = URL(URL).readText()//.getContent() as InputStream
            Log.d("stream", data)
            val jObject = JSONObject(data)
            val price = jObject.getDouble("USD");
            var time = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())

            var newEntry = "Price: $price$   Date: $time"


            if (MainActivity.history[0] == "EMPTY"){
                MainActivity.history.clear()
            }
            if (MainActivity.lastPrice != price){
                MainActivity.lastPrice = price
                if (MainActivity.history.size < 15){
                    MainActivity.history.add(0,newEntry)
                } else {
                    MainActivity.history.removeAt(14)
                    MainActivity.history.add(0,newEntry)
                }
            }

            GlobalScope.launch(Dispatchers.Main) {
                MainActivity.listAdapter.notifyDataSetChanged();
            }
        } catch (e : UnknownHostException){
            Log.d("mytag", "No internet")
        } catch (e : Exception) {
            Log.d("mytag", "Unknown exception: $e")
        }
        mHandler.postDelayed(mRunnable, 5000)
    }
}