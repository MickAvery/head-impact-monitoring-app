package com.example.ubcsimpllabheadimpactmonitoringapp.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelUuid
import android.widget.Button
import com.example.ubcsimpllabheadimpactmonitoringapp.databinding.ActivityLauncherBinding
import no.nordicsemi.android.support.v18.scanner.*

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding

    /* TODO: figure out correct filter */
    private var uuid: ParcelUuid = ParcelUuid.fromString("0000110A-0000-1000-8000-00805F9B34FB")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* find connect button */
        val connectButton: Button = binding.connectButton

        /* set button actions */
        connectButton.setOnClickListener {

            /* TODO: verify configs? these were taken from the example */
            val filters: MutableList<ScanFilter> = ArrayList()
            val scanner: BluetoothLeScannerCompat = BluetoothLeScannerCompat.getScanner()
            val scanSettings: ScanSettings = ScanSettings.Builder()
                                                        .setLegacy(false)
                                                        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                                                        .setReportDelay(1000)
                                                        .setUseHardwareBatchingIfSupported(true)
                                                        .build()

            /* apply filter and start scan */
            filters.add(ScanFilter.Builder().setServiceUuid(uuid).build())
            scanner.startScan(filters, scanSettings, scanCallback)
        }
    }

    /**
     * Scanning callback
     */
    private var scanCallback = object: ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>) {
            super.onBatchScanResults(results)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
        }
    }
}