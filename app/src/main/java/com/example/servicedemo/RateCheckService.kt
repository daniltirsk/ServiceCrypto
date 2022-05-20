package com.example.servicedemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.annotation.Nullable

class RateCheckService : Service() {

    @Nullable
    override fun onBind(intent: Intent)  = null
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}