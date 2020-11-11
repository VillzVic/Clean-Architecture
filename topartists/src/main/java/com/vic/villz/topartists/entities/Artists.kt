package com.vic.villz.topartists.entities

data class Artist(val name: String, val images: Map<ImageSize, String>, val expiry: Long) {

    enum class ImageSize {
        SMALL,
        MEDIUM,
        LARGE,
        EXTRA_LARGE,
        UNKNOWN
    }
}