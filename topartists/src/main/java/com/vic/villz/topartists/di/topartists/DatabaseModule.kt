package com.vic.villz.topartists.di.topartists

import android.app.Application
import androidx.room.Room
import com.vic.villz.core.app.providers.DataMapper
import com.vic.villz.core.app.providers.DataPersister
import com.vic.villz.topartists.database.*
import com.vic.villz.topartists.entities.Artist
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @Provides
    @JvmStatic
    internal  fun provideDatabase(context: Application): TopArtistsDatabase =
        Room.databaseBuilder(context, TopArtistsDatabase::class.java, "top-artists").build()

    @Provides
    @JvmStatic
    internal fun providesTopArtistsDao(database: TopArtistsDatabase) =
        database.topArtistsDao()

    @Provides
    @JvmStatic
    internal fun providesTopArtistsMapper(): DataMapper<Triple<Int, Artist, Long>, Pair<DbArtist, Collection<DbImage>>>
        = DatabaseTopArtistsMapper()

    @Provides
    @JvmStatic
    internal fun providesDatabasePersister(
        dao: TopArtistsDao,
        mapper: DataMapper<Triple<Int, Artist, Long>, Pair<DbArtist, Collection<DbImage>>>
    ): DataPersister<List<Artist>> =
        DatabaseTopArtistsPersister(dao, mapper)

}