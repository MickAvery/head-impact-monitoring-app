package com.example.ubcsimpllabheadimpactmonitoringapp.screens.datalogdownload

import androidx.lifecycle.ViewModel
import com.example.ubcsimpllabheadimpactmonitoringapp.DeviceModel

class DatalogDownloadViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    /**
     * Request to download datalogs from device
     */
    fun requestDatalogs() {
        DeviceModel.deviceRequestDatalogs()
    }
}