package com.vic.villz.core.app.providers

interface DataMapper<S, R> {
    fun map(source:S):R
}