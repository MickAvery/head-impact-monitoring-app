package com.example.ubcsimpllabheadimpactmonitoringapp.ble

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.Context
import android.util.Log
import com.example.ubcsimpllabheadimpactmonitoringapp.screens.MainActivity
import no.nordicsemi.android.ble.BleManager
import no.nordicsemi.android.ble.PhyRequest
import no.nordicsemi.android.ble.ReadRequest
import no.nordicsemi.android.ble.WriteRequest
import no.nordicsemi.android.ble.callback.DataReceivedCallback
import no.nordicsemi.android.ble.data.Data
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
     * Services and Characteristics UUIDs
     */
    private var mNusServiceUuid: UUID =
        UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E")
    private var mSimplServiceUuid: UUID =
        UUID.fromString("32A20001-ED70-480B-A945-866522F66758") /* NOTE: not a typo, the name of the custom service is SimpL service */
    private var mSimplServiceTxCharUuid: UUID =
        UUID.fromString("32A20002-ED70-480B-A945-866522F66758")
    private var mSimplServiceRxCharUuid: UUID =
        UUID.fromString("32A20003-ED70-480B-A945-866522F66758")
    private var mSimplServiceDevConfCharUuid: UUID =
        UUID.fromString("32A20004-ED70-480B-A945-866522F66758")

    /**
     * From perspective of the *device*, this is where the device transmits data to,
     * therefore this mobile app receives data from here!
     */
    private lateinit var mSimplDeviceTxCharacteristic: BluetoothGattCharacteristic

    /**
     * From perspective of the *device*, this is where it receives data
     */
    private lateinit var mSimplDeviceRxCharacteristic: BluetoothGattCharacteristic

    /**
     * Read Device Configurations from this characteristic
     */
    private lateinit var mSimplDeviceConfCharacteristic: BluetoothGattCharacteristic


    /**
     * Send array of bytes to device
     *
     * Returns a WriteRequest object which can be used to set callbacks.
     *
     * @return WriteRequest object for callbacks
     */
    fun sendBytesToDevice(bytes: ByteArray): WriteRequest {
        val ret: WriteRequest = writeCharacteristic(mSimplDeviceRxCharacteristic, bytes)
        ret.enqueue()

        return ret
    }

    /**
     * Set notification callback on Device Configs Characteristic.
     *
     * This callback is invoked when notifications are received.
     *
     * @param callback Callback to be invoked on notifications
     */
    fun setDevConfCharNotificationCallback(callback: DataReceivedCallback) {
        setNotificationCallback(mSimplDeviceConfCharacteristic).with(callback)
        enableNotifications(mSimplDeviceConfCharacteristic).enqueue()
    }

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
            var allServicesAndCharsDetected: Boolean = false
            val nusService: BluetoothGattService? = gatt.getService(this@AppBleManager.mNusServiceUuid)
            val simplService: BluetoothGattService? = gatt.getService(this@AppBleManager.mSimplServiceUuid)

            /* get characteristics from service */
            allServicesAndCharsDetected = nusService != null && simplService != null
            if( allServicesAndCharsDetected ) {
                mSimplDeviceTxCharacteristic   = simplService!!.getCharacteristic(mSimplServiceTxCharUuid)
                mSimplDeviceRxCharacteristic   = simplService.getCharacteristic(mSimplServiceRxCharUuid)
                mSimplDeviceConfCharacteristic = simplService.getCharacteristic(mSimplServiceDevConfCharUuid)
            }

            return allServicesAndCharsDetected
        }

        /**
         * Initialize the device.
         * Set required MTU and initial data.
         */
        override fun initialize() {
            beginAtomicRequestQueue()
                .add(requestMtu(247))
//                .add(setPreferredPhy(
//                    PhyRequest.PHY_LE_2M_MASK,
//                    PhyRequest.PHY_LE_2M_MASK,
//                    PhyRequest.PHY_OPTION_NO_PREFERRED))
                .add(enableNotifications(mSimplDeviceTxCharacteristic))
                .add(enableNotifications(mSimplDeviceConfCharacteristic))
                .enqueue()
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