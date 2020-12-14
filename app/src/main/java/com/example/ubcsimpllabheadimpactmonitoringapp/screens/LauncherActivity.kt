package com.example.ubcsimpllabheadimpactmonitoringapp.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelUuid
import android.util.Log
import android.widget.Button
import com.example.ubcsimpllabheadimpactmonitoringapp.databinding.ActivityLauncherBinding
import no.nordicsemi.android.support.v18.scanner.*

class LauncherActivity : AppCompatActivity() {

    /*
     *
     */
    private lateinit var binding: ActivityLauncherBinding

    /*
     *
     */
    private var PERMISSION_REQUEST_LOCATION: Int = 1

    /*
     * NOTE: BASE_UUID = 0000xxxx-0000-1000-8000-00805F9B34FB
     * WHERE: xxxx is the 16-bit UUID advertised by the device
     */
    private var uuid_uart: ParcelUuid = ParcelUuid.fromString("00000001-0000-1000-8000-00805F9B34FB")
    private var uuid_generic_dev: ParcelUuid = ParcelUuid.fromString("00001440-0000-1000-8000-00805F9B34FB")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* find connect button */
        val connectButton: Button = binding.connectButton

        /* set button actions */
        connectButton.setOnClickListener {

            /* We need to manually check ACCESS_FINE_LOCATION in runtime, see:
             * https://github.com/NordicSemiconductor/Android-Scanner-Compat-Library/issues/80
             * https://developer.android.com/training/permissions/requesting#manage-request-code-yourself
             */
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf<String> ( Manifest.permission.ACCESS_FINE_LOCATION ), PERMISSION_REQUEST_LOCATION)
            } else {
                startScan()
            }
        }
    }

    /**
     * If location permission requested, we land here on permission result
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            /* TODO: what to do if permission denied? */
            PERMISSION_REQUEST_LOCATION -> {
                startScan()
            }
            else ->
                Log.d("BLESCAN", "Location permission denied")
        }
    }

    /**
     * Start scanning for BLE devices
     */
    fun startScan() {
        val filters: MutableList<ScanFilter> = ArrayList()
        val scanner: BluetoothLeScannerCompat = BluetoothLeScannerCompat.getScanner()
        val scanSettings: ScanSettings = ScanSettings.Builder()
                                                    .setLegacy(false)
                                                    .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                                                    .setReportDelay(1000)
                                                    .setUseHardwareBatchingIfSupported(false)
                                                    .build()

        /* apply filters and start scan */
        filters.add(ScanFilter.Builder().setServiceUuid(uuid_uart).build())
        filters.add(ScanFilter.Builder().setServiceUuid(uuid_generic_dev).build())
        scanner.startScan(filters, scanSettings, scanCallback)
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
            /* TODO: add results to some list */
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
        }
    }
}