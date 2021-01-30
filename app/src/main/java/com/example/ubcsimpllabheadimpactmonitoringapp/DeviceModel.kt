package com.example.ubcsimpllabheadimpactmonitoringapp

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import com.example.ubcsimpllabheadimpactmonitoringapp.ble.AppBleManager
import no.nordicsemi.android.ble.ConnectRequest
import no.nordicsemi.android.ble.WriteRequest
import no.nordicsemi.android.ble.callback.DataReceivedCallback
import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback
import no.nordicsemi.android.ble.data.Data
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

    private var mDevConfCharCallback: DataReceivedCallback = object: DeviceConfigDataCallback() { }

    private enum class Requests(val req: Byte) {
        DEV_GET_CONFIGS  (0x00),
        DEV_SET_DATETIME (0x01),
        DEV_UPDATE_CONFIG(0x02)
    }

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

    /**
     * Get all important information from device, including:
     *   - Device configs
     *   - Current device datetime
     *   - If datalog file is ready for download
     *
     * Returns WriteRequest object which can be used to set callbacks.
     *
     * @return WriteRequest object which can be used to set callbacks
     */
    fun deviceGetConfigs(): WriteRequest {
        val bytes: ByteArray = byteArrayOf(Requests.DEV_GET_CONFIGS.req)
        return mBleManager.sendBytesToDevice(bytes)
    }

    override fun onDeviceReady(device: BluetoothDevice) {
        Log.d("BLECONN", "onDeviceReady")

        /* Set notification callback for Device Configs Characteristic */
        mBleManager.setDevConfCharNotificationCallback(mDevConfCharCallback)
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

    /**
     * Custom callback for Device Configs Characteristic
     *
     * @constructor
     */
    private abstract class DeviceConfigDataCallback: ProfileDataCallback {

        /**
         * Goes here if notification received on Device Configs Characteristic
         *
         * @param device
         * @param data
         */
        override fun onDataReceived(device: BluetoothDevice, data: Data) {
            Log.d("BLE" ,"$device --- ${data.size()} --- $data")
        }
    }

}