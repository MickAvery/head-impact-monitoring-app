package com.example.ubcsimpllabheadimpactmonitoringapp

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import com.example.ubcsimpllabheadimpactmonitoringapp.ble.AppBleManager
import no.nordicsemi.android.ble.ConnectRequest
import no.nordicsemi.android.ble.observer.ConnectionObserver

/**
 * Device model, represents IMU device that app is connected to.
 *
 * Contains all information about device (configuration, firmware rev, etc.)
 *
 * @constructor Create empty Device model
 */
object DeviceModel : ConnectionObserver {

    private lateinit var mBleManager: AppBleManager

    /**
     * Connect to a device
     *
     * @param device Device to connect to, discovered after a scan
     * @param context Current context
     * @return ConnectionRequest, allows caller to supply custom callbacks for successful or failed connections
     */
    fun connect(device: BluetoothDevice, context: Context): ConnectRequest {
        mBleManager = AppBleManager(context)
        mBleManager.setConnectionObserver(this)
        val connReq: ConnectRequest = mBleManager.connect(device)
                                                .timeout(10000)
                                                .retry(3, 100)

        /* Enqueue request for asynch execution */
        connReq.enqueue()

        return connReq
    }

    override fun onDeviceReady(device: BluetoothDevice) {
        /* Do nothing */
        Log.d("BLECONN", "onDeviceReady")
    }

    override fun onDeviceConnecting(device: BluetoothDevice) {
        /* Do nothing */
        Log.d("BLECONN", "onDeviceConnecting")
    }

    override fun onDeviceConnected(device: BluetoothDevice) {
        /* Do nothing */
        Log.d("BLECONN", "onDeviceConnected")
    }

    override fun onDeviceDisconnecting(device: BluetoothDevice) {
        /* Do nothing */
        Log.d("BLECONN", "onDeviceDisconnecting")
    }

    override fun onDeviceDisconnected(device: BluetoothDevice, reason: Int) {
        /* Do nothing */
        Log.d("BLECONN", "onDeviceDisconnected")
    }

    override fun onDeviceFailedToConnect(device: BluetoothDevice, reason: Int) {
        /* Do nothing */
        Log.d("BLECONN", "onDeviceFailedToConnect")
    }

}