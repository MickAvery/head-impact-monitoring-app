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

    /**
     * The first to be called upon creation of MainActivity
     *
     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DatalogDownloadViewModel::class.java)
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
        return inflater.inflate(R.layout.datalog_download_fragment, container, false)
    }

    /**
     * Perform logic on created views.
     * Set necessary texts fields
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = getString(R.string.datalog_download_screen_actionbar_title)
    }
}