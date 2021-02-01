package com.notificationexample

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        print("----------------------------------------------")
        intent?.hasExtra(MainActivity.MESSAGE_KEY).let {
            intent?.getStringExtra(MainActivity.MESSAGE_KEY).let {
                it?.let {
                    context?.toast(it)
                }
            }
            intent?.getIntExtra(MainActivity.NOTIFICATION_ID, 0).let {
                NotificationManagerCompat.from(context!!).cancel(MainActivity.notificationId)
            }
        }
    }
}
