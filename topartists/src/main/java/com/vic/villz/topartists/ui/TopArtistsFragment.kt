package com.vic.villz.topartists.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.vic.villz.topartists.R
import com.vic.villz.topartists.entities.Artist
import com.vic.villz.topartists.ui.adapter.TopArtistsAdapter
import com.vic.villz.topartists.viewmodels.TopArtistsViewModel
import dagger.android.AndroidInjection
import dagger.android.DaggerFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class TopArtistsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var topArtistsViewModel: TopArtistsViewModel
    private lateinit var topArtistsAdapter: TopArtistsAdapter

    private lateinit var topArtistsRecyclerView: RecyclerView
    private lateinit var retryButton: Button
    private lateinit var progress: ProgressBar
    private lateinit var errorMessage: TextView



    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topArtistsViewModel = ViewModelProviders.of(this, viewModelFactory).get(TopArtistsViewModel::class.java)
        topArtistsAdapter = TopArtistsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_top_artists, container, false).also { view ->
        topArtistsRecyclerView = view.findViewById(R.id.top_artists)
        retryButton = view.findViewById(R.id.retry)
        progress = view.findViewById(R.id.progress)
        errorMessage = view.findViewById(R.id.error_message)

        topArtistsRecyclerView.apply {
            adapter = topArtistsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        retryButton.setOnClickListener {
            topArtistsViewModel.load()
        }
        topArtistsViewModel.topArtistsViewState.observe(this, Observer { newState -> viewStateChanged(newState) })
    }

    private fun viewStateChanged(topArtistsViewState: TopArtistsViewState) {
        when (topArtistsViewState) {
            is TopArtistsViewState.InProgress -> setLoading()
            is TopArtistsViewState.ShowError -> setError(topArtistsViewState.message)
            is TopArtistsViewState.ShowTopArtists -> updateTopArtists(topArtistsViewState.topArtists)
        }
    }

    private fun setLoading() {
        progress.visibility = View.VISIBLE
        errorMessage.visibility = View.GONE
        retryButton.visibility = View.GONE
        topArtistsRecyclerView.visibility = View.GONE
    }

    private fun setError(message: String) {
        progress.visibility = View.GONE
        errorMessage.visibility = View.VISIBLE
        errorMessage.text = message
        retryButton.visibility = View.VISIBLE
        topArtistsRecyclerView.visibility = View.GONE
    }

    private fun updateTopArtists(topArtists: List<Artist>) {
        progress.visibility = View.GONE
        errorMessage.visibility = View.GONE
        retryButton.visibility = View.GONE
        topArtistsRecyclerView.visibility = View.VISIBLE
        topArtistsAdapter.replace(topArtists)
    }






}
