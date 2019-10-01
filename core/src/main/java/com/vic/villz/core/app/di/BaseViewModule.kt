package com.vic.villz.core.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vic.villz.core.app.view.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton
//import androidx.lifecycle.ViewModelProvider

@Module
@Suppress("unused")
abstract class BaseViewModule{

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}