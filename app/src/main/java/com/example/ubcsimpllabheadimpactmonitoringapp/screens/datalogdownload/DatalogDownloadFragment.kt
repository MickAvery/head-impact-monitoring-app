package com.example.ubcsimpllabheadimpactmonitoringapp.screens.datalogdownload

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.example.ubcsimpllabheadimpactmonitoringapp.R
import com.example.ubcsimpllabheadimpactmonitoringapp.databinding.ConfigurationFragmentBinding
import com.example.ubcsimpllabheadimpactmonitoringapp.databinding.DatalogDownloadFragmentBinding
import com.example.ubcsimpllabheadimpactmonitoringapp.screens.configuration.ConfigurationViewModel

class DatalogDownloadFragment : Fragment() {

    companion object {
        fun newInstance() = DatalogDownloadFragment()
    }
    /**
     * ViewModel and ViewBindings
     */
    private lateinit var mViewModel: DatalogDownloadViewModel
    private lateinit var mBinding: DatalogDownloadFragmentBinding


    /**
     * The first to be called upon creation of MainActivity
     *
     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(DatalogDownloadViewModel::class.java)
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
        /* get ViewBinding */
        mBinding = DatalogDownloadFragmentBinding.inflate(layoutInflater)
        return mBinding.root
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

        val downloadButton: Button = mBinding.buttonDatalogDownload
        downloadButton.setOnClickListener {
            mViewModel.requestDatalogs()
        }
    }
}