package com.notificationexample

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_DEFAULT
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import com.notificationexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

//    Note: It is not important for the setPriority to match with notification channel's because ths will be ignored anyway in android Oreo.
//          It will be only be used for device running Android Nougat or below android version.

    companion object {
        const val MESSAGE_KEY = "MESSAGE_KEY"
        const val NOTIFICATION_ID = "NOTIFICATION_ID"
        const val notificationId = 3

    }

    private var title: String = ""
    private var message: String = ""

    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        title = binding.editTitle.text.toString()
        message = binding.editDescription.text.toString()

        title = "Sample App"
        message = "I are working on different type of notifications."



        binding.editTitle.setText(title)
        binding.editDescription.setText(message)

        binding.btnOne.setOnClickListener(this::notificationType1)
        binding.btnTwo.setOnClickListener(this::notificationType2)
        binding.btnThree.setOnClickListener(this::notificationType3)
        binding.btnFour.setOnClickListener(this::notificationType4)


    }

    private fun checkValidation() {
        val title = binding.editTitle.text.toString()
        val message = binding.editDescription.text.toString()

        if (title.isEmpty()) {
            binding.editTitle.error = "Enter title"
            return
        }
        if (message.isEmpty()) {
            binding.editTitle.error = "Enter message"
            return
        }
    }

    private fun notificationType1(view: View) {

        checkValidation()
        hideKeyboard(view)

        val builder = NotificationCompat.Builder(this, App.CHANNEL_ONE_ID)
        builder.setSmallIcon(R.drawable.ic_one)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(IMPORTANCE_HIGH)
                .build()
        manager.notify(1, builder.build())
    }

    private fun notificationType2(view: View) {

        val title = binding.editTitle.text.toString()
        val message = binding.editDescription.text.toString()

        if (title.isEmpty()) {
            binding.editTitle.error = "Enter title"
            return
        }
        if (title.isEmpty()) {
            binding.editTitle.error = "Enter message"
            return
        }
        hideKeyboard(view)

        val builder = NotificationCompat.Builder(this, App.CHANNEL_TWO_ID)
        builder.setSmallIcon(R.drawable.ic_two)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(IMPORTANCE_DEFAULT)
                .build()

        manager.notify(2, builder.build())
    }

    private fun notificationType3(view: View) {

        checkValidation()
        hideKeyboard(view)

        val activityIntent = Intent(this, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0)

        val actionActivityIntent = Intent(this, SecondActivity::class.java)
        actionActivityIntent.apply {
            putExtra(MESSAGE_KEY, message)
            putExtra(NOTIFICATION_ID, notificationId)
        }
        val actionPendingActivityIntent = PendingIntent.getActivity(this, 0, actionActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val actionBroadcastReceiverIntent = Intent(this, NotificationBroadcastReceiver::class.java)
        actionBroadcastReceiverIntent.apply {
            putExtra(MESSAGE_KEY, message)
            putExtra(NOTIFICATION_ID, notificationId)
        }
        val actionPendingBroadcastReceiverIntent = PendingIntent.getBroadcast(this, 0, actionBroadcastReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val actionServiceIntent = Intent(this, MyIntentService::class.java)
        actionServiceIntent.apply {
            putExtra(MESSAGE_KEY, message)
            putExtra(NOTIFICATION_ID, notificationId)
        }
        val actionPendingServiceIntent = PendingIntent.getService(this, 0, actionServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val builder = NotificationCompat.Builder(this, App.CHANNEL_THREE_ID)
        builder.setSmallIcon(R.drawable.ic_three)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentPendingIntent)            //content for notification when user click on notification area
                .addAction(R.drawable.ic_launcher_background, "Start Activity", actionPendingActivityIntent)
                .addAction(R.drawable.ic_launcher_background, "Start BroadcastReceiver", actionPendingBroadcastReceiverIntent)
                .addAction(R.drawable.ic_launcher_background, "Start Service", actionPendingServiceIntent)
                .setPriority(IMPORTANCE_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.RED)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .build()

        manager.notify(notificationId, builder.build())
    }

    @SuppressLint("SetTextI18n")
    private fun notificationType4(view: View) {

        val msgStyle = binding.btnFour.text

        toast("set title and message in background")
        checkValidation()
        hideKeyboard(view)

        val activityIntent = Intent(this, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0)

        val largeIcon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.cat)

        val builder = NotificationCompat.Builder(this, App.CHANNEL_THREE_ID)
        builder.setSmallIcon(R.drawable.ic_three)
                .setContentTitle("Small Title")
                .setContentText("Small message hard text")
                .setLargeIcon(largeIcon)
                .setContentIntent(contentPendingIntent)            //content for notification when user click on notification area
                .setPriority(IMPORTANCE_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                .setOnlyAlertOnce(true)
                .setAutoCancel(true)

        when (msgStyle) {
            "BigTextStyle" -> {
                binding.btnFour.text = "InboxStyle"
                builder.setStyle(NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.long_dummy_text))
                        .setBigContentTitle("Big Content title")
                        .setSummaryText("abc@gmail.com"))
                        .setColor(Color.GREEN)
            }
            "InboxStyle" -> {
                binding.btnFour.text = "BigPictureStyle"
                builder.setStyle(NotificationCompat.InboxStyle()
                        .addLine("This is line no 1")
                        .addLine("This is line no 2")
                        .addLine("This is line no 3")
                        .addLine("This is line no 4")
                        .addLine("This is line no 5")
                        .addLine("This is line no 6")
                        .addLine("This is line no 7")
                        .addLine("This is line no 8")
                        .setBigContentTitle("Big Content title")
                        .setSummaryText("abc@gmail.com"))
                        .setColor(Color.RED)
            }
            "BigPictureStyle" -> {
                binding.btnFour.text = "BigTextStyle"
                builder.setStyle(NotificationCompat.BigPictureStyle()
                        .bigLargeIcon(null)
                        .bigPicture(largeIcon)
                        .setBigContentTitle("Big Content title"))
                        .setColor(Color.BLUE)
            }
//            https://youtu.be/s0Q2QKZ4OP8?t=255
//            "MediaStyle" -> {
//                binding.btnFour.text = "BigTextStyle"
//                builder.addAction(R.drawable.ic_launcher_background, "Dislike", null)
//                        .addAction(R.drawable.ic_launcher_background, "Previous", null)
//                        .addAction(R.drawable.ic_launcher_background, "Pause", null)
//                        .addAction(R.drawable.ic_launcher_background, "Next", null)
//                        .addAction(R.drawable.ic_launcher_background, "Like", null)
//                        .setStyle(NotificationCompat.MediaStyle()
//                                .setShowActionsInCompactView(1, 2, 3))
//                        .setSubText("sub text")
//                        .setColor(Color.RED)
////            }
        }
        builder.build()


        manager.notify(4, builder.build())
    }
}
























