package com.vic.villz.core.app.providers

interface UpdateScheduler <T> {
    fun scheduleUpdate(items: List<T>)
}