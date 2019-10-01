package com.vic.villz.topartists.entities

//make this guy generic in core module
sealed class TopArtistsState {

    object Loading : TopArtistsState()

    class Success(val artists: List<Artist>) : TopArtistsState()

    class Error(val message: String) : TopArtistsState()
}