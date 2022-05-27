package com.example.perfectweatherallyear

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.perfectweatherallyear.databinding.ActivityMainBinding
import com.example.perfectweatherallyear.util.WeatherBroadcastReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val broadcastReceiver = WeatherBroadcastReceiver()
        val intentFilter = IntentFilter("com.example.applicationfortestbroadcast.BROADCAST");
        registerReceiver(broadcastReceiver, intentFilter)
    }
}
