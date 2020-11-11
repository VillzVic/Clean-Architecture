package com.vic.villz.topartists.net

import com.vic.villz.core.app.providers.DataMapper
import com.vic.villz.topartists.entities.Artist
import javax.inject.Inject

//andd constructor @Inject so that you can use this in the repository
class LastFmArtistsMapper :DataMapper<Pair<LastFmArtists, Long>, List<Artist>> {


    override fun encode(source: Pair<LastFmArtists, Long>): List<Artist> {
        val (lastFmArtists, expiry) = source
        return lastFmArtists.artists.artist.map { artist ->
            Artist(artist.name, artist.normalisedImages(), expiry)
        }
    }


    private fun LastFmArtist.normalisedImages() =
        images.map { it.size.toImageSize() to it.url }.toMap()


    private fun String.toImageSize(): Artist.ImageSize =
        when (this) {
            "small" -> Artist.ImageSize.SMALL
            "medium" -> Artist.ImageSize.MEDIUM
            "large" -> Artist.ImageSize.LARGE
            "extralarge" -> Artist.ImageSize.EXTRA_LARGE
            else -> Artist.ImageSize.UNKNOWN
        }
}