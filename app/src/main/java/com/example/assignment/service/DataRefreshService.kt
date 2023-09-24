package com.example.assignment.service

import android.app.*
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.R
import com.example.assignment.viewmodel.ViewModelClass

class DataRefreshService : Service() {
    private val TAG = "com.example.assignment.service.DataRefreshService"
    private val REFRESH_INTERVAL:Long = 10 * 1000 // 10 seconds
    private val NOTIFICATION_CHANNEL_ID = "DataRefreshChannel"
    private val NOTIFICATION_ID = 1

    private lateinit var handler: Handler
    private lateinit var refreshRunnable: Runnable

    override fun onCreate() {
        super.onCreate()
        handler = Handler(Looper.getMainLooper())
        startForegroundService()
        refreshData()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun startForegroundService() {
        // Create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Data Refresh Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Create a notification for the foreground service
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Data Refresh Service")
            .setContentText("Refreshing data from API...")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .build()

        // Start the service as a foreground service with the notification
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun refreshData() {
        refreshRunnable = Runnable {
            // This will be called every 10 seconds
            val viewModel = ViewModelProvider.AndroidViewModelFactory(application)
                .create(ViewModelClass::class.java)
            viewModel.makeAPICall()
            Log.d(TAG, "Refreshing data from API...")
            handler.postDelayed(refreshRunnable, REFRESH_INTERVAL)
        }
        handler.post(refreshRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::handler.isInitialized && ::refreshRunnable.isInitialized) {
            handler.removeCallbacks(refreshRunnable)
        }
        // Stop the foreground service when the service is destroyed
        stopForeground(true)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
