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

    /**
     * The first to be called upon creation of MainActivity
     *
     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfigurationViewModel::class.java)
        // TODO: Use the ViewModel
    }

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
        /* set ActionBar title */
        activity?.title = getString(R.string.config_screen_actionbar_title)

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
    }

}