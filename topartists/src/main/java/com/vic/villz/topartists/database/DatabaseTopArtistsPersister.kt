package com.vic.villz.topartists.database

import com.vic.villz.core.app.providers.DataMapper
import com.vic.villz.core.app.providers.DataPersister
import com.vic.villz.topartists.entities.Artist

class DatabaseTopArtistsPersister (
    private val dao: TopArtistsDao,
    private val mapper: DataMapper<Triple<Int, Artist, Long>, Pair<DbArtist, Collection<DbImage>>>
) : DataPersister<List<Artist>>{



    override fun persistData(data: List<Artist>) {
        dao.deleteAll()

        val now = System.currentTimeMillis()
        val dbData = data.mapIndexed { index, artist ->
            mapper.encode(Triple(index, artist, now))
        }
        dao.insertTopArtists(dbData.map { it.first })
        dao.insertImages(dbData.flatMap { it.second })
    }

    override fun requestData(callback: (items: List<Artist>) -> Unit) {
        dao.deleteOutdated(System.currentTimeMillis())
        val dbImages = dao.getAllImages()
        val artists = dao.getAllArtists().sortedBy { it.rank }.map { artist ->
            mapper.decode(artist to dbImages.filter { it.rank == artist.rank }).second
        }
        callback(artists)
    }
}