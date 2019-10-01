package com.vic.villz.artists.di

import android.app.Application
import com.vic.villz.artists.MuseleeApplication
import com.vic.villz.topartists.di.topartists.TopArtistsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivitiesBuildersModule::class,
    TopArtistsModule::class,
    AppModule::class
])
@Singleton
interface AppComponent:AndroidInjector<MuseleeApplication>{


    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application) : Builder

        fun build(): AppComponent
    }

}