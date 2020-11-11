package com.vic.villz.artists.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.vic.villz.artists.MuseleeApplication
import com.vic.villz.core.app.connectivity.ConnectivityLiveData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule { //all other library modules can access this module and inject stuffs from here, if you need other module to access app level stuff use this format(so as to avoid cyclic dependency)

//    @Provides
//    @JvmStatic
//    @Singleton
//    internal fun provideApplication(app: MuseleeApplication): Application{  //this context can be provide to other module for use
//        return app
//    }


    @Provides
    @JvmStatic
    @Singleton
    fun provideConnectivityManager(app: Application): ConnectivityManager{
        return app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @JvmStatic
    @Singleton
    fun providesConnectivityLiveData(app: Application) =
        ConnectivityLiveData(app)

}