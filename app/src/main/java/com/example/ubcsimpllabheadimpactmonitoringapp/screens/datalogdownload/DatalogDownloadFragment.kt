package com.example.ubcsimpllabheadimpactmonitoringapp.screens.datalogdownload

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubcsimpllabheadimpactmonitoringapp.R

class DatalogDownloadFragment : Fragment() {

    companion object {
        fun newInstance() = DatalogDownloadFragment()
    }

    private lateinit var viewModel: DatalogDownloadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.datalog_download_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DatalogDownloadViewModel::class.java)
        // TODO: Use the ViewModel
    }

}