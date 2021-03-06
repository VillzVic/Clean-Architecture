package com.vic.villz.topartists.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vic.villz.topartists.R
import com.vic.villz.topartists.entities.Artist
import com.vic.villz.topartists.view.GridPositionCalculator
import com.vic.villz.topartists.view.ViewSize

internal class TopArtistsAdapter(
    private val calculator: GridPositionCalculator,
    private val items: MutableList<Artist> = mutableListOf()
): RecyclerView.Adapter<TopArtistsViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopArtistsViewHolder =TopArtistsViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(getLayoutId(viewType), parent, false)
    )

    override fun getItemViewType(position: Int): Int = calculator.getViewSize(position).ordinal

    private fun getLayoutId(viewType: Int) =
        when(viewType) {
            ViewSize.FULL.ordinal -> R.layout.item_chart_artist_full
            ViewSize.DOUBLE.ordinal -> R.layout.item_chart_artist_medium
            else -> R.layout.item_chart_artist_small
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TopArtistsViewHolder, position: Int) {
        items[position].also { artist ->
            val imageSize: Artist.ImageSize = when (calculator.getViewSize(position)) {
                ViewSize.FULL, ViewSize.DOUBLE -> Artist.ImageSize.EXTRA_LARGE
                ViewSize.TRIPLE -> Artist.ImageSize.LARGE
            }
            holder.bind(
                rank = (position + 1).toString(),
                artistName = artist.name,
                artistImageUrl = artist.images[imageSize] ?: artist.images.values.first()
            )
        }
    }

    fun replace(artists: List<Artist>){
        val difference = DiffUtil.calculateDiff(TopArtistsDiffUtil(items, artists))
        items.clear()
        items += artists
        difference.dispatchUpdatesTo(this)
    }

    private class TopArtistsDiffUtil(
        private val oldList: List<Artist>,
        private val newList: List<Artist>
    ): DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] === newList[newItemPosition]


        override fun getOldListSize(): Int = oldList.size


        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean  =
            oldList[oldItemPosition].name == newList[newItemPosition].name

    }

}