package com.example.ubcsimpllabheadimpactmonitoringapp

/**
 * Class to contain configuration enums for easy referencing
 *
 * @constructor Create empty Configurations
 */
class Configurations {

    /**
     * List of datalog mode options
     *
     * @constructor Create empty Datalog mode enum
     */
    enum class DatalogModeEnum(private val mode: String) {
        CONTINUOUS("Continuous"),
        TRIGGER("Trigger");

        override fun toString(): String {
            return mode
        }
    }

    /**
     * List of options for "Trigger on..." configuration.
     * Chooses which datapoint to trigger on.
     *
     * @constructor Create empty Trigger on enum
     */
    enum class TriggerOnEnum (private val trigger: String) {
        LINEAR_ACCELERATION("Linear Acceleration"),
        ANGULAR_VELOCITY("Angular Velocity");

        override fun toString(): String {
            return trigger
        }
    }

    /**
     * List of trigger axis options,
     * specifies which axis to trigger on to start datalogging
     *
     * @property triggerAxis
     * @constructor Create empty Trigger axis enum
     */
    enum class TriggerAxisEnum(private val triggerAxis: String) {
        RESULTANT("Resultant"),
        PER_AXIS("Per-axis");

        override fun toString(): String {
            return triggerAxis
        }
    }

    /**
     * List of gyroscope sampling rate options
     *
     * @property gyroSampleRate
     * @constructor Create empty Gyroscope sampling enum
     */
    enum class GyroscopeSamplingEnum(private val gyroSampleRate: String) {
        GYRO_SAMPLE_4500HZ("4500 Hz"),
        GYRO_SAMPLE_2000HZ("2000 Hz"),
        GYRO_SAMPLE_1000HZ("1000 Hz"),
        GYRO_SAMPLE_500HZ("500 Hz"),
        GYRO_SAMPLE_250HZ("250 Hz"),
        GYRO_SAMPLE_125HZ("125 Hz");

        override fun toString(): String {
            return gyroSampleRate
        }
    }

    /**
     * List of low-G accelerometer (<= 30g) sampling rate options
     *
     * @property lowGAccSampleRate
     * @constructor Create empty Low g accelerometer sampling enum
     */
    enum class LowGAccelerometerSamplingEnum(private val lowGAccSampleRate: String) {
        LOW_G_ACC_SAMPLE_4500HZ("4500 Hz"),
        LOW_G_ACC_SAMPLE_2000HZ("2000 Hz"),
        LOW_G_ACC_SAMPLE_1000HZ("1000 Hz"),
        LOW_G_ACC_SAMPLE_500HZ("500 Hz"),
        LOW_G_ACC_SAMPLE_250HZ("250 Hz"),
        LOW_G_ACC_SAMPLE_125HZ("125 Hz");

        override fun toString(): String {
            return lowGAccSampleRate
        }
    }

    /**
     * List of high-G accelerometer (<= 200g) sampling rate options
     *
     * @property highGAccSampleRate
     * @constructor Create empty High g accelerometer sampling enum
     */
    enum class HighGAccelerometerSamplingEnum(private val highGAccSampleRate: String) {
        HIGH_G_ACC_SAMPLE_6400HZ("6400 Hz"),
        HIGH_G_ACC_SAMPLE_3200HZ("3200 Hz"),
        HIGH_G_ACC_SAMPLE_1600HZ("1600 Hz"),
        HIGH_G_ACC_SAMPLE_800HZ("800 Hz"),
        HIGH_G_ACC_SAMPLE_400HZ("400 Hz");

        override fun toString(): String {
            return highGAccSampleRate
        }
    }
}