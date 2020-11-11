package com.vic.villz.topartists.net

import com.vic.villz.core.app.ConnectivityChecker
import com.vic.villz.core.app.providers.DataMapper
import com.vic.villz.core.app.providers.DataProvider
import com.vic.villz.topartists.entities.Artist
import com.vic.villz.topartists.entities.TopArtistsState
import okhttp3.internal.http.HttpDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


//I can do this in my repository and add inject to the constructors(the datamapper will be listArtistDatamapaer)
class LastFmTopArtistsProvider(
    private val topArtistsApi: LastFmTopArtistsApi,
    private val connectivityChecker: ConnectivityChecker,
    private val mapper: DataMapper<Pair<LastFmArtists, Long>, List<Artist>>
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
                    callback(TopArtistsState.Success(mapper.encode(topArtists to response.expiry)))
                }
            }
        })
    }

    override fun requestData(): TopArtistsState {
        return if(!connectivityChecker.isConnected){
            TopArtistsState.Error("No network connectivity")
        } else {
            val response = topArtistsApi.getTopArtists().execute()
            response.takeIf { it.isSuccessful}?.body()?.let { artists ->
                TopArtistsState.Success(mapper.encode(artists to response.expiry))
            } ?: TopArtistsState.Error(response.errorBody()?.string() ?: "Network Error")  //sweet
        }
    }

    private val Response<LastFmArtists>.expiry: Long
        get() {
            //Use EXPIRES header if it exists
            val expires: Long? = if (headers().names().contains(HEADER_EXPIRES)) {
                HttpDate.parse(headers().get(HEADER_EXPIRES)).time
            } else null
            //Use Cache-Control header if that exists
            val cacheControlMaxAge = raw().cacheControl().maxAgeSeconds().toLong()

            //Use Access-Control-Max-Age if that exists
            val maxAge: Long? =
                cacheControlMaxAge.takeIf { it >= 0 } ?: headers().get(HEADER_AC_MAX_AGE)?.toLong()

            //Defaults to one day
            val date = if (headers().names().contains(HEADER_DATE)) {
                HttpDate.parse(headers().get(HEADER_DATE)).time
            } else {
                System.currentTimeMillis()
            }
            return expires
                ?: maxAge?.let { date + TimeUnit.SECONDS.toMillis(it) }
                ?: date + TimeUnit.DAYS.toMillis(1)
        }

    companion object {
        private const val HEADER_DATE = "Date"
        private const val HEADER_EXPIRES = "Expires"
        private const val HEADER_AC_MAX_AGE = "Access-Control-Max-Age"
    }

}