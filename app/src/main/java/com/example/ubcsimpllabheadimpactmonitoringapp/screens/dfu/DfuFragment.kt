package com.example.ubcsimpllabheadimpactmonitoringapp.screens.dfu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubcsimpllabheadimpactmonitoringapp.R

class DfuFragment : Fragment() {

    companion object {
        fun newInstance() = DfuFragment()
    }

    private lateinit var viewModel: DfuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dfu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DfuViewModel::class.java)
        // TODO: Use the ViewModel
    }

}