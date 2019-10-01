package com.vic.villz.artists.di

import com.vic.villz.artists.MuseleeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMuseleeActivity(): MuseleeActivity
}