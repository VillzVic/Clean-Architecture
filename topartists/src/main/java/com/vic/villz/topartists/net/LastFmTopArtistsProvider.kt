package com.vic.villz.topartists.net

import com.vic.villz.core.app.ConnectivityChecker
import com.vic.villz.core.app.providers.DataMapper
import com.vic.villz.core.app.providers.DataProvider
import com.vic.villz.topartists.entities.Artist
import com.vic.villz.topartists.entities.TopArtistsState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//I can do this in my repository and add inject to the constructors(the datamapper will be listArtistDatamapaer)
class LastFmTopArtistsProvider(
    private val topArtistsApi: LastFmTopArtistsApi,
    private val connectivityChecker: ConnectivityChecker,
    private val mapper: DataMapper<LastFmArtists, List<Artist>>
): DataProvider<TopArtistsState>{


    override fun requestData(callback: (topArtists: TopArtistsState) -> Unit) {
        if (!connectivityChecker.isConnected) {
            callback(TopArtistsState.Error("No network connectivity"))
            return
        }
        callback(TopArtistsState.Loading)
        //convert to rxjava single
        topArtistsApi.getTopArtists().enqueue(object : Callback<LastFmArtists> {
            override fun onFailure(call: Call<LastFmArtists>, t: Throwable) {
                callback(TopArtistsState.Error(t.localizedMessage))
            }

            override fun onResponse(call: Call<LastFmArtists>, response: Response<LastFmArtists>) {
                response.body()?.also { topArtists ->
                    callback(TopArtistsState.Success(mapper.map(topArtists)))
                }
            }
        })
    }


}