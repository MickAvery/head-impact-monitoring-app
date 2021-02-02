package com.example.ubcsimpllabheadimpactmonitoringapp.screens.configuration

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

    var mDatalogEnabled: Boolean =
        false

    /**
     * Set device configs
     */
    fun setDeviceConfigs(
        threshold_resultant: String,
        threshold_x: String,
        threshold_y: String,
        threshold_z: String
    ) {
        var readyToSet = true

        if(mDatalogMode == Configurations.DatalogModeEnum.TRIGGER) {

            if(mTriggerAxis == Configurations.TriggerAxisEnum.RESULTANT) {

                if(threshold_resultant == "") {
                    readyToSet = false
                } else {
                    mTrigThresholdResultant = threshold_resultant.toShort()
                }

            } else {

                if(threshold_x == "" || threshold_y == "" || threshold_z == "") {
                    readyToSet = false
                } else {
                    mTrigThresholdX = threshold_x.toShort()
                    mTrigThresholdY = threshold_y.toShort()
                    mTrigThresholdZ = threshold_z.toShort()
                }
            }
        }

        if(readyToSet) {
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

    /**
     * Toggle datalog enable configuration
     */
    fun toggleDatalogEnable() {
        DeviceModel.deviceToggleDatalogEnable()
    }
}