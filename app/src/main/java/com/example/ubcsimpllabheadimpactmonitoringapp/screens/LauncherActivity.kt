package com.example.ubcsimpllabheadimpactmonitoringapp.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelUuid
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ubcsimpllabheadimpactmonitoringapp.R
import com.example.ubcsimpllabheadimpactmonitoringapp.databinding.ActivityLauncherBinding
import kotlinx.android.synthetic.main.device_list_row.view.*
import no.nordicsemi.android.support.v18.scanner.*

class LauncherActivity : AppCompatActivity() {

    /*
     *
     */
    private lateinit var mBinding: ActivityLauncherBinding

    /*
     *
     */
    private var PERMISSION_REQUEST_LOCATION: Int = 1

    /*
     * NOTE: BASE_UUID = 0000xxxx-0000-1000-8000-00805F9B34FB
     * WHERE: xxxx is the 16-bit UUID advertised by the device
     */
    private var mUuidUart: ParcelUuid = ParcelUuid.fromString("00000001-0000-1000-8000-00805F9B34FB")
    private var mUuidGenericDev: ParcelUuid = ParcelUuid.fromString("00001440-0000-1000-8000-00805F9B34FB")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        /* find connect button */
        val connectButton: Button = mBinding.connectButton

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
    private fun startScan() {
        val filters: MutableList<ScanFilter> = ArrayList()
        val scanner: BluetoothLeScannerCompat = BluetoothLeScannerCompat.getScanner()
        val scanSettings: ScanSettings = ScanSettings.Builder()
                                                    .setLegacy(false)
                                                    .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                                                    .setReportDelay(1000)
                                                    .setUseHardwareBatchingIfSupported(false)
                                                    .build()

        /* apply filters and start scan */
        filters.add(ScanFilter.Builder().setServiceUuid(mUuidUart).build())
        filters.add(ScanFilter.Builder().setServiceUuid(mUuidGenericDev).build())
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
            /* TODO: This is called multiple times after scanning, what to do? */
            val recyclerView: RecyclerView = mBinding.devicesList
            recyclerView.layoutManager = LinearLayoutManager(this@LauncherActivity)
            val deviceAdapter = DeviceRecyclerAdapter()
            recyclerView.adapter = deviceAdapter
            deviceAdapter.submitList(results)
        }

        override fun onScanFailed(errorCode: Int) {
            /* TODO: what to do on fail? */
            super.onScanFailed(errorCode)
        }
    }
}

/**
 * TODO: DOCUMENTATION!!!
 */
class DeviceRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItems: MutableList<ScanResult> = ArrayList()

    /**
     * Creates individual view holders
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DeviceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.device_list_row, parent, false)
        )
    }

    /**
     * Set data in list
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DeviceViewHolder -> {
                holder.bind(mItems[position])
            }
        }
    }

    /**
     * Returns number of items in list
     */
    override fun getItemCount(): Int {
        return mItems.size
    }

    /**
     * Set list of devices
     */
    fun submitList(devList: MutableList<ScanResult>) {
        mItems = devList
    }

    /**
     * Custom Viewholder class, describes each item in viewholder
     */
    class DeviceViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {

        private val deviceRowName: TextView = itemView.dev_row_name
        private val deviceRowSubname: TextView = itemView.dev_row_subname

        fun bind(device: ScanResult) {
            deviceRowName.text = device.scanRecord?.deviceName
            deviceRowSubname.text = "1"
        }
    }

}
