package com.notificationexample

import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import android.graphics.Color
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// TODO: Rename actions, choose action names that describe tasks that this
//
class MyIntentService : IntentService("MyIntentService") {


    override fun onCreate() {
        super.onCreate()
        println("onCreate")
    }

    override fun onHandleIntent(intent: Intent?) {
        println("onHandleIntent")


        startForeground(1001, getNotification())
        intent?.hasExtra(MainActivity.MESSAGE_KEY).let {

            intent?.getIntExtra(MainActivity.NOTIFICATION_ID, 0).let {
                NotificationManagerCompat.from(applicationContext)
                    .cancel(MainActivity.notificationId)
            }

            intent?.getStringExtra(MainActivity.MESSAGE_KEY).let {
                it?.let {
                    println("----------- $it")

                    var counter = 1;
                    repeat(10)
                    {
                        SystemClock.sleep(1000)
                        println("--------------------${counter++}")
                    }
                }
            }

        }
    }

    private fun getNotification(): Notification? {
        val builder = NotificationCompat.Builder(this, App.CHANNEL_THREE_ID)
        builder.setSmallIcon(R.drawable.ic_background)
            .setContentTitle("Service Notification")
            .setContentText("A Background service running")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        return builder.build()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
        stopForeground(true)
    }

}