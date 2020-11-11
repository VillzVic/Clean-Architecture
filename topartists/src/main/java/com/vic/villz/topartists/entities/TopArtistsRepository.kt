package com.vic.villz.topartists.entities

import com.vic.villz.core.app.providers.DataPersister
import com.vic.villz.core.app.providers.DataProvider
import com.vic.villz.core.app.providers.UpdateScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TopArtistsRepository(
    private val persister: DataPersister<List<Artist>>,
    private val provider: DataProvider<TopArtistsState>,
    private val scheduler: UpdateScheduler<Artist>
) : DataProvider<TopArtistsState> {

    override fun requestData(callback: (item: TopArtistsState) -> Unit) =
        //calling from the db
        persister.requestData { artists ->
            if (artists.isEmpty()) {
                //request from api if db is empty(data there has been expired)
                provider.requestData { state -> //asynchronous
                    if (state is TopArtistsState.Success) {
                        //still persist data even when the viewmodel is cancelled
                        GlobalScope.launch(Dispatchers.IO) {
                            persister.persistData(state.artists)
                        }
                        scheduler.scheduleUpdate(state.artists)
                    }
                    callback(state)
                }
            } else {
                callback(TopArtistsState.Success(artists))
            }
        }
}