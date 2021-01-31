package com.example.ubcsimpllabheadimpactmonitoringapp.screens.configuration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import com.example.ubcsimpllabheadimpactmonitoringapp.Configurations
import com.example.ubcsimpllabheadimpactmonitoringapp.R
import com.example.ubcsimpllabheadimpactmonitoringapp.databinding.ConfigurationFragmentBinding

class ConfigurationFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigurationFragment()
    }

    /**
     * ViewModel and ViewBindings
     */
    private val mViewModel: ConfigurationViewModel by viewModels()
//    private val mViewModel: ConfigurationViewModel =
//        ViewModelProvider(this).get(ConfigurationViewModel::class.java)
    private lateinit var mBinding: ConfigurationFragmentBinding

    /**
     * Fragment views for easy referencing
     */
    private lateinit var mDatalogModeSpinner: Spinner
    private lateinit var mTriggerOnSpinner: Spinner
    private lateinit var mTrigThresholdResultant: EditText
    private lateinit var mTrigThresholdX: EditText
    private lateinit var mTrigThresholdY: EditText
    private lateinit var mTrigThresholdZ: EditText
    private lateinit var mConfigDevBtn: Button
    private lateinit var mDatalogBtn: Button

    /**
     * Have fragment instantiate its interface views
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* get ViewBinding */
        mBinding = ConfigurationFragmentBinding.inflate(layoutInflater)
        return mBinding.root
    }

    /**
     * Operate on views that were created in onCreateView()
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* set ActionBar title */
        activity?.title = getString(R.string.config_screen_actionbar_title)

        /*
         * Get EditText objects
         */
        mTrigThresholdResultant = mBinding.edittextTriggerThresholdResultant
        mTrigThresholdX = mBinding.edittextTriggerThresholdX
        mTrigThresholdY = mBinding.edittextTriggerThresholdY
        mTrigThresholdZ = mBinding.edittextTriggerThresholdZ

        /*
         * set Spinner contents
         */

        /* "Datalog Mode" spinner, get values from DatalogModeEnum */
        mDatalogModeSpinner = mBinding.spinnerDatalogMode
        mDatalogModeSpinner.adapter =
            ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                Configurations.DatalogModeEnum.values()
            )

        /* "Trigger on..." spinner, get values from TriggerOnEnum */
        mTriggerOnSpinner = mBinding.spinnerTriggerOn
        mTriggerOnSpinner.adapter =
            ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                Configurations.TriggerOnEnum.values()
            )

        /* "Trigger axis" spinner, get values from TriggerAxisEnum */
        val triggerAxisSpinner: Spinner = mBinding.spinnerTriggerAxis
        triggerAxisSpinner.adapter =
            ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                Configurations.TriggerAxisEnum.values()
            )

        /* "Gyroscope sample rate" spinner */
        val gyroSampleRateSpinner: Spinner = mBinding.spinnerSamplingRateGyro
        gyroSampleRateSpinner.adapter =
            ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                Configurations.GyroscopeSamplingEnum.values()
            )

        /* "Low-G Accelerometer sample rate" spinner */
        val lowGAccSampleRateSpinner: Spinner = mBinding.spinnerSamplingRateLowGAccel
        lowGAccSampleRateSpinner.adapter =
            ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                Configurations.LowGAccelerometerSamplingEnum.values()
            )

        /* "High-G Accelerometer sample rate" spinner */
        val highGAccSampleRateSpinner: Spinner = mBinding.spinnerSamplingRateHighGAccel
        highGAccSampleRateSpinner.adapter =
            ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                Configurations.HighGAccelerometerSamplingEnum.values()
            )

        /*
         * Set view listeners
         */

        /* "Datalog Mode" spinner listener */
        mDatalogModeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemSelected: Configurations.DatalogModeEnum =
                    Configurations.DatalogModeEnum.values()[position]

                when( itemSelected ) {
                    Configurations.DatalogModeEnum.CONTINUOUS -> {
                        /* Hide trigger configurations */
                        mBinding.layoutTriggerViewgroup.visibility = View.GONE
                    }
                    Configurations.DatalogModeEnum.TRIGGER -> {
                        /* Set visibility of other trigger configurations */
                        mBinding.layoutTriggerViewgroup.visibility = View.VISIBLE
                    }
                }

                /* save selected item to viewmodel */
                mViewModel.mDatalogMode = itemSelected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                /* Do nothing */
            }
        } /* AdapterView.OnItemSelectedListener */

        /* "Trigger on..." spinner listener */
        mTriggerOnSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                /* save selected item to viewmodel */
                mViewModel.mTriggerOn = Configurations.TriggerOnEnum.values()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                /* Do nothing */
            }
        } /* AdapterView.OnItemSelectedListener */

        /* "Trigger Axis" spinner listener */
        triggerAxisSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemSelected: Configurations.TriggerAxisEnum =
                    Configurations.TriggerAxisEnum.values()[position]

                when( itemSelected ) {
                    Configurations.TriggerAxisEnum.RESULTANT -> {
                        /* Only show resultant threshold configuration option */
                        mBinding.layoutTriggerThresholdResultant.visibility = View.VISIBLE
                        /* Hide per-axis configuration options */
                        mBinding.layoutTriggerPerAxisViewgroup.visibility = View.GONE
                    }
                    Configurations.TriggerAxisEnum.PER_AXIS -> {
                        /* Hide resultant threshold configuration option */
                        mBinding.layoutTriggerThresholdResultant.visibility = View.GONE
                        /* Only show per-axis configuration options */
                        mBinding.layoutTriggerPerAxisViewgroup.visibility = View.VISIBLE
                    }
                }

                mViewModel.mTriggerAxis = itemSelected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                /* Do nothing */
            }
        } /* AdapterView.OnItemSelectedListener */

        /* "Gyroscope Sampling Rate" spinner listener */
        gyroSampleRateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemSelected: Configurations.GyroscopeSamplingEnum =
                    Configurations.GyroscopeSamplingEnum.values()[position]

                mViewModel.mGyroSamplingRate = itemSelected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                /* Do nothing */
            }
        }

        /* "Low-G Accelerometer Sampling Rate" spinner listener */
        lowGAccSampleRateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemSelected: Configurations.LowGAccelerometerSamplingEnum =
                    Configurations.LowGAccelerometerSamplingEnum.values()[position]

                mViewModel.mLowGAccelSamplingRate = itemSelected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                /* Do nothing */
            }
        }

        /* "High-G Accelerometer Sampling Rate" spinner listener */
        highGAccSampleRateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemSelected: Configurations.HighGAccelerometerSamplingEnum =
                    Configurations.HighGAccelerometerSamplingEnum.values()[position]

                mViewModel.mHighGAccelSamplingRate = itemSelected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                /* Do nothing */
            }
        }

        /**
         * Configure SET DEVICE CONFIGS button
         */
        mConfigDevBtn = mBinding.buttonConfigureDevice
        mConfigDevBtn.setOnClickListener {

            mViewModel.setDeviceConfigs(
                mTrigThresholdResultant.text.toString(),
                mTrigThresholdX.text.toString(),
                mTrigThresholdY.text.toString(),
                mTrigThresholdZ.text.toString())

        }

        mDatalogBtn = mBinding.buttonStartDatalog
        mDatalogBtn.setOnClickListener {
            mViewModel.toggleDatalogEnable()
        }
    }
}