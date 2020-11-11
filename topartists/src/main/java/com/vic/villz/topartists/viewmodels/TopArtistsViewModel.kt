package com.vic.villz.topartists.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vic.villz.core.app.connectivity.ConnectivityLiveData
import com.vic.villz.core.app.providers.DataProvider
import com.vic.villz.topartists.di.topartists.TopArtistsModule
import com.vic.villz.topartists.entities.TopArtistsState
import com.vic.villz.topartists.ui.TopArtistsViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class TopArtistsViewModel @Inject constructor(
    @Named(TopArtistsModule.ENTITIES) private val topArtistsProvider: DataProvider<TopArtistsState>,
    val connectivityLiveData: ConnectivityLiveData
): ViewModel(){

    private val mutableLiveData: MutableLiveData<TopArtistsViewState> = MutableLiveData()

    val topArtistsViewState:LiveData<TopArtistsViewState>
        get() = mutableLiveData

    init {
        load()
    }

    fun load() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            topArtistsProvider.requestData { artistsState ->
                update(artistsState)

            }
        }
    }

    private fun update(artistsState: TopArtistsState) = viewModelScope.launch {
        withContext(Dispatchers.Main) {
            mutableLiveData.value = when (artistsState) {
                TopArtistsState.Loading -> TopArtistsViewState.InProgress
                is TopArtistsState.Error -> TopArtistsViewState.ShowError(artistsState.message)
                is TopArtistsState.Success -> TopArtistsViewState.ShowTopArtists(artistsState.artists)
            }
        }
    }

}
