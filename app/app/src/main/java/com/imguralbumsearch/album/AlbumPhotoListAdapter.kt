package com.imguralbumsearch.album

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.imguralbumsearch.R
import com.imguralbumsearch.databinding.PhotoItemViewBinding
import com.imguralbumsearch.rpc.Image

/** An adapter class that binds photo objects to the RecyclerView that displays album photos. */
class AlbumPhotoListAdapter(private val images: List<Image>) :
    RecyclerView.Adapter<AlbumPhotoListAdapter.AlbumPhotoViewHolder>() {

    override fun getItemCount() = images.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumPhotoViewHolder {
        val viewBinding = PhotoItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, /* attachToParent= */false
        )
        return AlbumPhotoViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: AlbumPhotoViewHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
    }

    /** A ViewHolder class that holds the view of a single photo. */
    class AlbumPhotoViewHolder(private val viewBinding: PhotoItemViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(image: Image) {
            // TODO: figure out how to load GIFV file.
            val thumbnailUrl = if (image.mimeType == "video/mp4") {
                image.gifUrl
            } else {
                image.url
            }

            Glide.with(viewBinding.root)
                .load(thumbnailUrl)
                // Use a non-image placeholder so that in the beginning the image won't be cropped
                // to the size of the placeholder image.
                .placeholder(
                    ColorDrawable(
                        ContextCompat.getColor(
                            viewBinding.root.context,
                            R.color.image_placeholder_color
                        )
                    )
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(viewBinding.photo)
        }
    }
}