package com.vic.villz.topartists.schedulers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.vic.villz.core.app.providers.DataPersister
import com.vic.villz.core.app.providers.DataProvider
import com.vic.villz.core.app.providers.UpdateScheduler
import com.vic.villz.core.app.work.DaggerWorkerFactory
import com.vic.villz.topartists.di.topartists.TopArtistsModule
import com.vic.villz.topartists.entities.Artist
import com.vic.villz.topartists.entities.TopArtistsState
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Named

class TopArtistsUpdateWorker (
    private val provider: DataProvider<TopArtistsState>,
    private val persister: DataPersister<List<Artist>>,
    private val scheduler: UpdateScheduler<Artist>,
    context: Context,
    workerParams: WorkerParameters
    ) : Worker(context, workerParams) {


    override fun doWork(): Result =
        when(val state = provider.requestData()) { //using the synchronous form of requestData cos we are in the background
            is TopArtistsState.Success -> {
                persister.persistData(state.artists)
                scheduler.scheduleUpdate(state.artists) //if the data was successful, schedule it again
                Result.success()
            }
            is TopArtistsState.Error -> Result.retry()
            is TopArtistsState.Loading -> throw IllegalStateException("Unexpected Loading State")
        }

    class Factory @Inject constructor(
        @Named(TopArtistsModule.NETWORK) private val provider: DataProvider<TopArtistsState>,
        private val persister: DataPersister<List<Artist>>,
        private val scheduler: UpdateScheduler<Artist>
    ) : DaggerWorkerFactory.ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker =
            TopArtistsUpdateWorker(provider, persister, scheduler, appContext, params)
    }


}