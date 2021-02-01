package com.notificationexample

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App : Application() {

    companion object {
        const val CHANNEL_ONE_ID: String = "channel_one_id"
        const val CHANNEL_TWO_ID: String = "channel_two_id"
        const val CHANNEL_THREE_ID: String = "channel_three_id"
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelOne = NotificationChannel(
                CHANNEL_ONE_ID,
                "Channel One",
                NotificationManager.IMPORTANCE_HIGH
            )

            val channelTwo = NotificationChannel(
                CHANNEL_TWO_ID,
                "Channel Two",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val channelThree = NotificationChannel(
                CHANNEL_THREE_ID,
                "Channel Three",
                NotificationManager.IMPORTANCE_HIGH
            )

//            val manager = getSystemService(NotificationManager::class.java)
//            or
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

//            one bye one create notification channel
//            manager.createNotificationChannel(channelOne)
//            manager.createNotificationChannel(channelTwo)
//                or
            val list: List<NotificationChannel> = mutableListOf(
                channelOne,
                channelTwo,
                channelThree
            )
            manager.createNotificationChannels(list)
        }
    }
}




































