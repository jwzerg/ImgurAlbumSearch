package com.imguralbumsearch.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.imguralbumsearch.R
import com.imguralbumsearch.databinding.AlbumItemViewBinding
import com.imguralbumsearch.rpc.Album

/** An adapter class that binds an album to the view. */
class AlbumListViewAdapter(private val onClickAlbumListener: OnClickAlbumListener) :
    ListAdapter<Album, AlbumListViewAdapter.AlbumItemViewHolder>(AlbumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding =
            AlbumItemViewBinding.inflate(inflater, parent, /* attachToParent= */ false)
        return AlbumItemViewHolder(viewBinding, onClickAlbumListener)
    }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album)
    }

    class AlbumItemViewHolder(
        private val viewBinding: AlbumItemViewBinding,
        private val onClickAlbumListener: OnClickAlbumListener
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(album: Album) {
            viewBinding.albumTitle.text = album.title

            // TODO: figure out how to load GIFV file.
            val image = album.images[0]
            val thumbnailUrl = if (image.mimeType == "video/mp4") {
                image.gifUrl
            } else {
                image.url
            }

            Glide.with(viewBinding.root)
                .load(thumbnailUrl)
                .placeholder(R.drawable.image_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(viewBinding.albumThumbnail)

            viewBinding.root.setOnClickListener {
                onClickAlbumListener.onClickAlbum(album)
            }
        }
    }

    /** A listener triggered when users tap on an album. */
    interface OnClickAlbumListener {
        fun onClickAlbum(album: Album)
    }
}

/** Callback for calculating the diff between two non-null items in a list. */
private class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}
