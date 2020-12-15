package com.example.ubcsimpllabheadimpactmonitoringapp.ble

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattService
import android.content.Context
import android.util.Log
import no.nordicsemi.android.ble.BleManager
import java.util.*

/**
 * Our BLE Manager implementation. See:
 * https://github.com/NordicSemiconductor/Android-BLE-Library#usage
 *
 * @property context current context
 * @constructor Creates a BLE manager
 */
class AppBleManager(context: Context) : BleManager(context) {

    /**
     * M nus uuid
     */
    private var mNusServiceUuid: UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E")
    private var mSimplServiceUuid: UUID = UUID.fromString("32A20001-ED70-480B-A945-866522F66758") /* NOTE: not a typo, the name of the custom service is SimpL service */

    override fun getGattCallback(): BleManagerGattCallback {
        return AppBleManagerGattCallback()
    }

    /**
     * Bluetooth GATT callbacks object
     *
     * @constructor Creates a BleManagerGattCallback object
     */
    private inner class AppBleManagerGattCallback : BleManagerGattCallback() {

        /**
         * Will be called when the device is connected and services are discovered.
         * You need to obtain references to the characteristics and descriptors that you will use.
         *
         * @return true if all required services are found, false otherwise.
         */
        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
            /* TODO: Add custom services as necessary */
            /* check if requried services are implemented */
            val nusService: BluetoothGattService? = gatt.getService(this@AppBleManager.mNusServiceUuid)
            val simplService: BluetoothGattService? = gatt.getService(this@AppBleManager.mSimplServiceUuid)

            return nusService != null && simplService != null
        }

        /**
         * Initialize the device.
         * Set required MTU and initial data.
         */
        override fun initialize() {
            requestMtu(247).enqueue()
        }

        /**
         * Device disconnected, release references here
         */
        override fun onDeviceDisconnected() {
            /* TODO: figure out what to do here */
            /* Do nothing */
            Log.d("BLECONN", "onDeviceDisconnected")
        }

    }

}