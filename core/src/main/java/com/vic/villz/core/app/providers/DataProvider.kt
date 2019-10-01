package com.vic.villz.core.app.providers

interface DataProvider<T> {
    fun requestData(callback: (items:T) -> Unit)
}