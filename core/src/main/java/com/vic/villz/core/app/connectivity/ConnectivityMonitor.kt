package com.vic.villz.core.app.connectivity

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build

internal sealed class ConnectivityMonitor (protected val connectivityManager: ConnectivityManager){  //this does not support synchronous checking
    protected var callbackFunction: ((Boolean) -> Unit) = {}

    abstract fun startListening(callback: (Boolean) -> Unit)
    abstract fun stopListening()

    @TargetApi(Build.VERSION_CODES.N)
    private class NougatConnectivityMonitor(connectivityManager: ConnectivityManager) :
        ConnectivityMonitor(connectivityManager) {

        private val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                callbackFunction(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                callbackFunction(false)
            }
        }

        override fun startListening(callback: (Boolean) -> Unit) {
            callbackFunction = callback
            callbackFunction(false) //start the listening
            connectivityManager.registerDefaultNetworkCallback(networkCallback) // always listen for network changes
        }

        override fun stopListening() {
            connectivityManager.unregisterNetworkCallback(networkCallback)
            callbackFunction = {}
        }
    }


    @Suppress("Deprecation")
    private class LegacyConnectivityMonitor(
        private val context: Context,
        connectivityManager: ConnectivityManager
    ) : ConnectivityMonitor(connectivityManager) {

        private val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION) //always listen for network changes

        private val isNetworkConnected: Boolean
            get() = connectivityManager.activeNetworkInfo?.isConnected == true

        override fun startListening(callback: (Boolean) -> Unit) {
            callbackFunction = callback
            callbackFunction(isNetworkConnected) //first check
            context.registerReceiver(receiver, filter) //whenever connectivity changes then use this receiver to change it
        }

        override fun stopListening() {
            context.unregisterReceiver(receiver)
            callbackFunction = {}
        }

        private val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                callbackFunction(isNetworkConnected)
            }
        }
    }

    companion object {
        fun getInstance(context: Context): ConnectivityMonitor{
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NougatConnectivityMonitor(connectivityManager)
            } else {
                LegacyConnectivityMonitor(context, connectivityManager)
            }
        }
    }
}