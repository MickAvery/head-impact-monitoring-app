package com.example.ubcsimpllabheadimpactmonitoringapp.screens.devterminal

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubcsimpllabheadimpactmonitoringapp.R

class DevTerminalFragment : Fragment() {

    companion object {
        fun newInstance() = DevTerminalFragment()
    }

    private lateinit var viewModel: DevTerminalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dev_terminal_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DevTerminalViewModel::class.java)
        // TODO: Use the ViewModel
    }

}