package com.vic.villz.core.app.providers

interface DataPersister<T> : DataProvider<T>{
    fun persistData(data:T)
}