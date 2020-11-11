package com.vic.villz.artists

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.vic.villz.artists.di.DaggerAppComponent
import com.vic.villz.core.app.work.DaggerWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MuseleeApplication : Application(), HasAndroidInjector{

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var workerFactory: DaggerWorkerFactory

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().application(this).build().inject(this)

        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        )
    }

    override fun androidInjector() = androidInjector


}