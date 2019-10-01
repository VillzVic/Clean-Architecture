package com.vic.villz.topartists.ui

import com.vic.villz.topartists.entities.Artist

sealed class TopArtistsViewState {

    object InProgress : TopArtistsViewState()

    class ShowTopArtists(val topArtists: List<Artist>) : TopArtistsViewState()

    class ShowError(val message: String) : TopArtistsViewState()
}