package com.vic.villz.topartists.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vic.villz.core.app.providers.DataProvider
import com.vic.villz.topartists.entities.TopArtistsState
import com.vic.villz.topartists.net.LastFmTopArtistsProvider
import com.vic.villz.topartists.ui.TopArtistsViewState
import javax.inject.Inject

class TopArtistsViewModel @Inject constructor(
    private val topArtistsProvider: DataProvider<TopArtistsState>
): ViewModel(){

    private val mutableLiveData: MutableLiveData<TopArtistsViewState> = MutableLiveData()

    val topArtistsViewState:LiveData<TopArtistsViewState>
        get() = mutableLiveData

    init {
        load()
    }

    fun load() {
        topArtistsProvider.requestData { artistsState ->
            mutableLiveData.value = when (artistsState){
                TopArtistsState.Loading -> TopArtistsViewState.InProgress
                is TopArtistsState.Error -> TopArtistsViewState.ShowError(artistsState.message)
                is TopArtistsState.Success -> TopArtistsViewState.ShowTopArtists(artistsState.artists)


            }
        }
    }

}
