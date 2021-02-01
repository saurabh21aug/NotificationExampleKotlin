package com.notificationexample

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.notificationexample.MainActivity.Companion.MESSAGE_KEY
import com.notificationexample.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent?.apply {
            val msg = intent.getStringExtra(MESSAGE_KEY)
            msg?.let {
                toast(it)
                binding.textView.text = it
            }
            NotificationManagerCompat.from(applicationContext)
                .cancel(intent.getIntExtra(MainActivity.NOTIFICATION_ID, 0))
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
