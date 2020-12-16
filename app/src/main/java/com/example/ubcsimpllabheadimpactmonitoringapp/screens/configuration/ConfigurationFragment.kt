package com.example.ubcsimpllabheadimpactmonitoringapp.screens.configuration

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.ubcsimpllabheadimpactmonitoringapp.R
import com.example.ubcsimpllabheadimpactmonitoringapp.databinding.ConfigurationFragmentBinding

class ConfigurationFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigurationFragment()
    }

    private lateinit var viewModel: ConfigurationViewModel
    private lateinit var mBinding: ConfigurationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* get ViewBinding */
        mBinding = ConfigurationFragmentBinding.inflate(layoutInflater)

        /* set ActionBar title */
        activity?.title = "Configure Device"

        /* set Spinner contents */
        val datalogModeSpinner: Spinner = mBinding.spinnerDatalogMode
        ArrayAdapter.createFromResource(
            requireActivity().applicationContext,
            R.array.string_array_datalog_mode_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            datalogModeSpinner.adapter = adapter
        }

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfigurationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}