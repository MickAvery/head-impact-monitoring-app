package com.example.ubcsimpllabheadimpactmonitoringapp.screens.configuration

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ubcsimpllabheadimpactmonitoringapp.Configurations
import com.example.ubcsimpllabheadimpactmonitoringapp.DeviceModel

/**
 * Configuration view model, helps to save state of configuration screen,
 * middleman between configuration screen views and device model
 *
 * @constructor Create empty Configuration view model
 */
class ConfigurationViewModel(private val state: SavedStateHandle) : ViewModel() {
    /**
     * Some useful constants
     */
    companion object {
        private val DATALOG_MODE = "datalogMode"
    }

    private val vmState = state

    var mDatalogMode: Configurations.DatalogModeEnum =
        Configurations.DatalogModeEnum.CONTINUOUS

    var mTriggerOn: Configurations.TriggerOnEnum =
        Configurations.TriggerOnEnum.LINEAR_ACCELERATION

    var mTriggerAxis: Configurations.TriggerAxisEnum =
        Configurations.TriggerAxisEnum.RESULTANT

    var mTrigThresholdResultant: Short = 0

    var mTrigThresholdX: Short = 0

    var mTrigThresholdY: Short = 0

    var mTrigThresholdZ: Short = 0

    var mGyroSamplingRate: Configurations.GyroscopeSamplingEnum =
        Configurations.GyroscopeSamplingEnum.GYRO_SAMPLE_4500HZ

    var mLowGAccelSamplingRate: Configurations.LowGAccelerometerSamplingEnum =
        Configurations.LowGAccelerometerSamplingEnum.LOW_G_ACC_SAMPLE_4500HZ

    var mHighGAccelSamplingRate: Configurations.HighGAccelerometerSamplingEnum =
        Configurations.HighGAccelerometerSamplingEnum.HIGH_G_ACC_SAMPLE_6400HZ

    /**
     * Set device configs
     *
     */
    fun setDeviceConfigs() {
        DeviceModel.deviceSetConfigs(
            mDatalogMode,
            mTriggerOn,
            mTriggerAxis,
            mTrigThresholdResultant,
            mTrigThresholdX,
            mTrigThresholdY,
            mTrigThresholdZ,
            mGyroSamplingRate,
            mLowGAccelSamplingRate,
            mHighGAccelSamplingRate
        )
    }
}