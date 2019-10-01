package com.vic.villz.topartists.di.topartists

import com.vic.villz.core.app.ConnectivityChecker
import com.vic.villz.core.app.providers.DataMapper
import com.vic.villz.core.app.providers.DataProvider
import com.vic.villz.topartists.entities.Artist
import com.vic.villz.topartists.entities.TopArtistsState
import com.vic.villz.topartists.net.LastFmArtists
import com.vic.villz.topartists.net.LastFmArtistsMapper
import com.vic.villz.topartists.net.LastFmTopArtistsApi
import com.vic.villz.topartists.net.LastFmTopArtistsProvider
import dagger.Module
import dagger.Provides

@Module
object LastFmTopArtistsModule{


    @Provides
    @JvmStatic
    fun provideLastFmMapper(): DataMapper<LastFmArtists, List<Artist>> = LastFmArtistsMapper()

    @Provides
    @JvmStatic
    fun providesTopArtistsProvider(
        lastFmTopArtistsApi: LastFmTopArtistsApi,
        connectivityChecker: ConnectivityChecker,
        mapper: DataMapper<LastFmArtists, List<Artist>>
    ): DataProvider<TopArtistsState> = LastFmTopArtistsProvider(
        lastFmTopArtistsApi,
        connectivityChecker,
        mapper
    )

}