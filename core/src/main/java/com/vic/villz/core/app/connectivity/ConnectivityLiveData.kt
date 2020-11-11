package com.vic.villz.core.app.connectivity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject


class ConnectivityLiveData @Inject constructor(context: Context) : MutableLiveData<ConnectivityState>() {

    private val connectionMonitor = ConnectivityMonitor.getInstance(context.applicationContext)

    override fun onInactive() {

        connectionMonitor.stopListening()
        super.onInactive()

    }

    override fun onActive() {

        super.onActive()
        connectionMonitor.startListening(::setConnected)
    }

    private fun setConnected(isConnected: Boolean) =
        postValue(if (isConnected) ConnectivityState.Connected else ConnectivityState.Disconnected)

}