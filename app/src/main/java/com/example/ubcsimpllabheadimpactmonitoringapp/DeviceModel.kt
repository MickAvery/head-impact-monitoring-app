package com.example.ubcsimpllabheadimpactmonitoringapp

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import no.nordicsemi.android.ble.ConnectRequest
import no.nordicsemi.android.ble.observer.ConnectionObserver

object DeviceModel : ConnectionObserver {

    private lateinit var mBleManager: AppBleManager

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