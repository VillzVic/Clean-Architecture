package com.vic.villz.topartists.di.topartists

import com.vic.villz.core.app.providers.DataPersister
import com.vic.villz.core.app.providers.DataProvider
import com.vic.villz.core.app.providers.UpdateScheduler
import com.vic.villz.topartists.di.topartists.TopArtistsModule
import com.vic.villz.topartists.entities.Artist
import com.vic.villz.topartists.entities.TopArtistsRepository
import com.vic.villz.topartists.entities.TopArtistsState
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object EntitiesModule {


    @Provides
    @Named(TopArtistsModule.ENTITIES)
    @JvmStatic
    internal fun providesTopArtistsRepository(
        persistence: DataPersister<List<Artist>>,
        @Named(TopArtistsModule.NETWORK) networkProvider: DataProvider<TopArtistsState>,
        scheduler: UpdateScheduler<Artist>
    ): DataProvider<TopArtistsState> = TopArtistsRepository(persistence, networkProvider, scheduler)
}