package com.vic.villz.topartists.net

import com.vic.villz.core.app.providers.DataMapper
import com.vic.villz.topartists.entities.Artist
import javax.inject.Inject

//andd constructor @Inject so that you can use this in the repository
class LastFmArtistsMapper :DataMapper<LastFmArtists, List<Artist>>{


    override fun map(source: LastFmArtists): List<Artist> =
        source.artists.artist.map { artist ->
            Artist(artist.name, artist.normalisedImages())
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