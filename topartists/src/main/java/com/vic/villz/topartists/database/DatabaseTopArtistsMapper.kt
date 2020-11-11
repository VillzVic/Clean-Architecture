package com.vic.villz.topartists.database

import com.vic.villz.core.app.providers.DataMapper
import com.vic.villz.topartists.entities.Artist

class DatabaseTopArtistsMapper : DataMapper<Triple<Int, Artist, Long>, Pair<DbArtist, Collection<DbImage>>> {


    override fun encode(source: Triple<Int, Artist, Long>): Pair<DbArtist, Collection<DbImage>> {
        val (rank, artist, created) = source

        return DbArtist(rank, artist.name, created, artist.expiry) to artist.images.map { DbImage(rank, it.key.ordinal, it.value) }
    }

    override fun decode(source: Pair<DbArtist, Collection<DbImage>>): Triple<Int, Artist, Long> {
        val (artist, images) = source

        return Triple(
            artist.rank,
            Artist(artist.name,
                    images.map { Artist.ImageSize.values()[it.typeIndex] to it.url}.toMap(),
                     artist.expiry
                ),
            artist.created
        )

    }

}