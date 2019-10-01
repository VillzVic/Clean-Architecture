package com.vic.villz.core.app.di

import dagger.Module

@Module
object CoreDataModule {

    //provide database module here, then each other module can have a datamodule that provides specific Daos and has entities etc.
}