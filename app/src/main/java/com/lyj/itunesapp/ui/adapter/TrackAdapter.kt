package com.lyj.itunesapp.ui.adapter

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.lyj.itunesapp.R
import com.lyj.itunesapp.core.base.BaseAdapter
import com.lyj.itunesapp.core.extension.android.resDrawable
import com.lyj.itunesapp.core.extension.lang.simpleTag


class TrackAdapter(
    override var viewModel: TrackAdapterViewModel) :
    BaseAdapter<TrackData, TrackAdapter.TrackViewHolder>(
        viewModel, R.layout.item_track
    ) {

    override fun generateViewHolder(view: View): TrackViewHolder = TrackViewHolder(view)

    override fun onBindViewHolder(holder: TrackViewHolder, position : Int ,data: TrackData) {
        holder.apply {
            trackName.text = data.trackName
            collectionName.text = data.collectionName
            artistName.text = data.artistName
            btnFavorite.setImageDrawable(resDrawable(if (data.isFavorite != null && data.isFavorite) R.drawable.ic_star_normal else R.drawable.ic_star_inverted))

            Glide
                .with(viewModel.context)
                .load(data.url)
                .into(imageView)

            viewModel
                .onFavoriteButtonClick(
                    btnFavorite.clicks().map { data.trackId }
                )
        }
    }

    inner class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trackName: TextView = view.findViewById(R.id.trackTxtTrackName)
        val collectionName: TextView = view.findViewById(R.id.trackTxtCollectionName)
        val artistName: TextView = view.findViewById(R.id.trackTxtArtistName)
        val imageView: ImageView = view.findViewById(R.id.trackImgAlbum)
        val btnFavorite: AppCompatImageButton = view.findViewById(R.id.trackBtnFavorite)
    }
}