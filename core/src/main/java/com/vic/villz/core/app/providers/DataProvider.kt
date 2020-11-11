package com.vic.villz.core.app.providers

interface DataProvider<T> {
    //asynchronous
    fun requestData(callback: (items:T) -> Unit)

    //synchronous
    fun requestData() : T = throw NotImplementedError() //throw this error so any existing implementations of this interface will still compile

}