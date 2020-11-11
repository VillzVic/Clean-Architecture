package com.vic.villz.topartists.schedulers

import androidx.work.*
import com.vic.villz.core.app.providers.UpdateScheduler
import com.vic.villz.topartists.entities.Artist
import java.util.concurrent.TimeUnit

class TopArtistsScheduler() : UpdateScheduler<Artist>{

    override fun scheduleUpdate(items: List<Artist>) {
        WorkManager.getInstance()
            .enqueueUniqueWork(
                UNIQUE_WORK_ID,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequestBuilder<TopArtistsUpdateWorker>()
                    .setInitialDelay(items.earliestUpdate(), TimeUnit.MILLISECONDS)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.UNMETERED)
                            .setRequiresBatteryNotLow(true)
                            .build()
                    ).build()
                )

    }

    private fun List<Artist>.earliestUpdate() =
        (minBy { it.expiry }?.expiry?.let { it - System.currentTimeMillis() }
            ?: TimeUnit.DAYS.toMillis(1)) / 2

    companion object {
        private const val UNIQUE_WORK_ID: String = "TopArtistsScheduler"
    }

}