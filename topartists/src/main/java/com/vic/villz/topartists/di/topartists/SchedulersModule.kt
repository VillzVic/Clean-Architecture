package com.vic.villz.topartists.di.topartists

import com.vic.villz.core.app.providers.UpdateScheduler
import com.vic.villz.topartists.entities.Artist
import com.vic.villz.topartists.schedulers.TopArtistsScheduler
import dagger.Module
import dagger.Provides

@Module
object SchedulerModule {

    @Provides
    @JvmStatic
    fun providesScheduler(): UpdateScheduler<Artist> =
        TopArtistsScheduler()

}