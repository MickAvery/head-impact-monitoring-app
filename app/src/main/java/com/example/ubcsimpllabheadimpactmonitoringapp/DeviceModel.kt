package com.example.ubcsimpllabheadimpactmonitoringapp

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.icu.util.Calendar
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

    private var mDatalogModeEnabled: Boolean = false

    /**
     * Notification callbacks to be invoked when being notified by changes on an characteristic attribute
     */
    private var mDevConfCharCallback: DataReceivedCallback = object: DeviceConfigDataCallback() { }
    private var mTxCharCallback: DataReceivedCallback = object: TxCharacteristicDataCallback() { }

    private enum class Requests(val req: Byte) {
        DEV_GET_CONFIGS   (0x00),
        DEV_SET_CONFIG    (0x01),
        DEV_SET_DATETIME  (0x02),
        DEV_START_DATALOG (0x03),
        DEV_STOP_DATALOG  (0x04),
        DEV_REQUEST_LOGS  (0x05)
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

    /**
     * Set device configurations
     */
    fun deviceSetConfigs(
        datalogMode: Configurations.DatalogModeEnum,
        triggerOn: Configurations.TriggerOnEnum,
        triggerAxis: Configurations.TriggerAxisEnum,
        triggerThresholdResultant: Short,
        triggerThresholdX: Short,
        triggerThresholdY: Short,
        triggerThresholdZ: Short,
        gyroSampleRate: Configurations.GyroscopeSamplingEnum,
        lowGAccSampleRate: Configurations.LowGAccelerometerSamplingEnum,
        highGAccSampleRate: Configurations.HighGAccelerometerSamplingEnum
    ): WriteRequest {
        var bytes: ByteArray = byteArrayOf(Requests.DEV_SET_CONFIG.req)
        bytes += datalogMode.ordinal.toByte()
        bytes += triggerOn.ordinal.toByte()
        bytes += triggerAxis.ordinal.toByte()
        bytes += shortToByteArray(triggerThresholdResultant)
        bytes += shortToByteArray(triggerThresholdX)
        bytes += shortToByteArray(triggerThresholdY)
        bytes += shortToByteArray(triggerThresholdZ)
        bytes += gyroSampleRate.ordinal.toByte()
        bytes += lowGAccSampleRate.ordinal.toByte()
        bytes += highGAccSampleRate.ordinal.toByte()

        return mBleManager.sendBytesToDevice(bytes)
    }

    /**
     * Set device datetime to match that of mobile app.
     *
     * Returns WriteRequest object which can be used to set callbacks.
     *
     * @return WriteRequest object which can be used to set callbacks
     */
    fun deviceSetDatetime(): WriteRequest {
        val currentDatetime = Calendar.getInstance()
        val year: Short = (currentDatetime.get(Calendar.YEAR)).toShort()
        val month: Byte = (currentDatetime.get(Calendar.MONTH) + 1).toByte()
        val day: Byte = currentDatetime.get(Calendar.DATE).toByte()
        val hour: Byte = currentDatetime.get(Calendar.HOUR_OF_DAY).toByte()
        val minute: Byte = currentDatetime.get(Calendar.MINUTE).toByte()
        val sec: Byte = currentDatetime.get(Calendar.SECOND).toByte()
        val ms: Int = currentDatetime.get(Calendar.MILLISECOND)

        var bytes: ByteArray = byteArrayOf(Requests.DEV_SET_DATETIME.req)
        bytes += shortToByteArray(year)
        bytes += month
        bytes += day
        bytes += hour
        bytes += minute
        bytes += sec
        bytes += intToByteArray(ms)

        Log.d("BLE", "$year -- $month -- $day -- $hour -- $minute -- $sec -- $ms")
        Log.d("BLE", "$bytes")
        return mBleManager.sendBytesToDevice(bytes)
    }

    /**
     * Depending on whether datalogging is happening, request
     * device to stop/start datalogging.
     *
     * Returns WriteRequest object which can be used to set callbacks.
     *
     * @return WriteRequest object which can be used to set callbacks
     */
    fun deviceToggleDatalogEnable(): WriteRequest {
        var bytes: ByteArray = byteArrayOf(Requests.DEV_START_DATALOG.req)

        if (mDatalogModeEnabled) {
            bytes = byteArrayOf(Requests.DEV_STOP_DATALOG.req)
        }

        mDatalogModeEnabled =  !mDatalogModeEnabled

        return mBleManager.sendBytesToDevice(bytes)
    }

    /**
     * Send a request to download datalogs from device
     *
     * Returns WriteRequest object which can be used to set callbacks.
     *
     * @return WriteRequest object which can be used to set callbacks
     */
    fun deviceRequestDatalogs(): WriteRequest {
        var request: ByteArray = byteArrayOf(Requests.DEV_REQUEST_LOGS.req)

        return mBleManager.sendBytesToDevice(request)
    }

    /**
     * Convert Short type to ByteArray in little endian
     *
     * @param short Short to convert
     * @return ByteArray in little endian
     */
    private fun shortToByteArray(short: Short): ByteArray {
        return byteArrayOf( (short.toInt() and 0x00FF).toByte(), ((short.toInt() and 0xFF00) shr 8).toByte() )
    }

    /**
     * Convert Int type to ByteArray in little endian
     *
     * @param int Int to convert
     * @return ByteArray in little endian
     */
    private fun intToByteArray(int: Int): ByteArray {
        return byteArrayOf(
            (int and 0xFF).toByte(),
            ((int and 0xFF00) shr 8).toByte(),
            ((int and 0xFF0000) shr 16).toByte(),
            ((int and 0xFF000000.toInt()) shr 24).toByte()
        )
    }

    override fun onDeviceConnecting(device: BluetoothDevice) {
        /* Do nothing */
        Log.d("BLECONN", "onDeviceConnecting")
    }

    override fun onDeviceConnected(device: BluetoothDevice) {
        /* Do nothing */
        Log.d("BLECONN", "onDeviceConnected")
    }

    override fun onDeviceReady(device: BluetoothDevice) {
        Log.d("BLECONN", "onDeviceReady")

        /* Set notification callback for Device Configs Characteristic */
        mBleManager.setDevConfCharNotificationCallback(mDevConfCharCallback)
        mBleManager.setTxCharNotificationCallback(mTxCharCallback)
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

    /**
     * Custom callback for Tx Characteristic
     *
     * @constructor
     */
    private abstract class TxCharacteristicDataCallback: ProfileDataCallback {
        /**
         * Goes here if notification received on TX Characteristic
         *
         * @param device
         * @param data
         */
        override fun onDataReceived(device: BluetoothDevice, data: Data) {
            Log.d("BLE" ,"$device --- ${data.size()}")
        }
    }
}