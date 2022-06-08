package com.example.perfectweatherallyear

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.perfectweatherallyear.databinding.ActivityMainBinding
import com.example.perfectweatherallyear.util.NotificationHandler
//import com.example.perfectweatherallyear.util.WorkerHandler

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStop() {
        super.onStop()
        NotificationHandler.createNotificationChannel(this.application)
    //    WorkerHandler.createWorker(this)
        }
}