package com.example.services.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.services.activities.MainActivity
import com.example.services.R

class ForegroundService: Service() {
    private val OWN_CHANNEL = "IE_CHANNEL_ID"

    companion object{
        private val IE_EXTRA = "Foreground Service"

        fun startService(context: Context, message: String){
            val start = Intent(context, ForegroundService::class.java)
            start.putExtra(IE_EXTRA, message)
            ContextCompat.startForegroundService(context, start)
        }

        fun stopService (context: Context){
            val end = Intent(context, ForegroundService::class.java)
            context.stopService(end)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra(IE_EXTRA)
        createNotification()
        val notiIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, 0)

        val notification = NotificationCompat.Builder(this, OWN_CHANNEL)
            .setContentTitle("Foreground Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    private fun createNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(OWN_CHANNEL, "Notification Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}