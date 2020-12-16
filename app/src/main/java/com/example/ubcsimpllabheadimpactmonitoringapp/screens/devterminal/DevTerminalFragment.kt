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

    /**
     * The first to be called upon creation of MainActivity
     *
     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DevTerminalViewModel::class.java)
        // TODO: Use the ViewModel
    }

    /**
     * Fragment instantiates its views
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
        return inflater.inflate(R.layout.dev_terminal_fragment, container, false)
    }

    /**
     * Operate on instantiated views
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = getString(R.string.dev_terminal_screen_actionbar_title)
    }

}