package com.vic.villz.topartists.di.topartists

import androidx.lifecycle.ViewModel
import com.vic.villz.core.app.di.BaseViewModule
import com.vic.villz.core.app.di.ViewModelKey
import com.vic.villz.core.app.di.WorkerKey
import com.vic.villz.core.app.work.DaggerWorkerFactory
import com.vic.villz.topartists.schedulers.TopArtistsUpdateWorker
import com.vic.villz.topartists.ui.TopArtistsFragment
import com.vic.villz.topartists.viewmodels.TopArtistsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    DatabaseModule::class,
    NetworkModule::class,
    BaseViewModule::class,
    EntitiesModule::class,
    SchedulerModule::class,
    LastFmTopArtistsModule::class])
abstract class TopArtistsModule {

    companion object {
        const val ENTITIES = "ENTITIES"
        const val NETWORK = "NETWORK"
    }

    @ContributesAndroidInjector
    abstract fun contributeTopFragment(): TopArtistsFragment

    @Binds
    @IntoMap
    @ViewModelKey(TopArtistsViewModel::class)
    abstract fun bindChartsViewModel(viewModel: TopArtistsViewModel): ViewModel  //makes TopArtistsViewModel available to the ViewModelFactory that we created in the Core module

    @Binds
    @IntoMap
    @WorkerKey(TopArtistsUpdateWorker::class)
    abstract fun bindTopArtistsUpdateWorker(factory: TopArtistsUpdateWorker.Factory):
            DaggerWorkerFactory.ChildWorkerFactory

}