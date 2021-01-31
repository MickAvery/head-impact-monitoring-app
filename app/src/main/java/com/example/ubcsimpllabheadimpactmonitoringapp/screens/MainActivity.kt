package com.example.ubcsimpllabheadimpactmonitoringapp.screens

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ubcsimpllabheadimpactmonitoringapp.DeviceModel
import com.example.ubcsimpllabheadimpactmonitoringapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        /* get device configs */
        DeviceModel.deviceGetConfigs().fail { _, status ->
            Log.d("BLE", "Failed to get configs...($status)")
        }

        /* update device datetime */
        DeviceModel.deviceSetDatetime()
    }
}