package com.imguralbumsearch.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.imguralbumsearch.R
import com.imguralbumsearch.databinding.AlbumListViewBinding
import com.imguralbumsearch.rpc.Album
import com.imguralbumsearch.utils.Result

private const val STAGGERED_GRID_LAYOUT_SPANS = 2

/** An object that represents the state of an AlbumListView. */
data class AlbumListViewState(
    val showAlbum: Boolean = true,
    val albums: List<Album> = listOf(),
)

/** The view that displays a list of albums. */
class AlbumListView : FrameLayout, AlbumListViewAdapter.OnClickAlbumListener {

    private val viewBinding: AlbumListViewBinding
    private val albumListViewAdapter = AlbumListViewAdapter(this)

    private var viewListener: AlbumListViewListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int
    ) : this(context, attrs, defStyleAttr, 0)

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        viewBinding = AlbumListViewBinding.inflate(
            LayoutInflater.from(context), this, /* attachToParent= */true
        )

        viewBinding.albumList.apply {
            adapter = albumListViewAdapter
            layoutManager = StaggeredGridLayoutManager(/* spanCount= */ STAGGERED_GRID_LAYOUT_SPANS,
                RecyclerView.VERTICAL
            )
        }
    }

    override fun onClickAlbum(album: Album) {
        checkNotNull(viewListener).onClickAlbum(album)
    }

    fun bindView(viewData: Result<AlbumListViewState>, listener: AlbumListViewListener) {
        this.viewListener = listener

        when (viewData) {
            is Result.Loading -> showLoadingSpinner()
            is Result.Error -> showErrorMessage()
            else -> {
                val viewState = (viewData as Result.Success<AlbumListViewState>).data
                if (!viewState.showAlbum) {
                    // This only happens when this view is shown for the first time.
                    hideView()
                } else {
                    if (viewState.albums.isEmpty()) {
                        showNoAlbumFoundMessage()
                    } else {
                        showAlbums(viewState)
                    }
                }
            }
        }
    }

    private fun hideView() {
        viewBinding.message.visibility = View.GONE
        viewBinding.albumList.visibility = View.GONE
        viewBinding.loadingSpinner.visibility = View.GONE
    }

    private fun showLoadingSpinner() {
        viewBinding.message.visibility = View.GONE
        viewBinding.albumList.visibility = View.GONE

        viewBinding.loadingSpinner.visibility = View.VISIBLE
    }

    private fun showErrorMessage() {
        viewBinding.loadingSpinner.visibility = View.GONE
        viewBinding.albumList.visibility = View.GONE

        viewBinding.message.apply {
            text = resources.getString(R.string.error_message)
            visibility = View.VISIBLE
        }
    }

    private fun showNoAlbumFoundMessage() {
        viewBinding.loadingSpinner.visibility = View.GONE
        viewBinding.albumList.visibility = View.GONE

        viewBinding.message.apply {
            text = resources.getString(R.string.no_album_found_message)
            viewBinding.message.visibility = View.VISIBLE
        }
    }

    private fun showAlbums(viewState: AlbumListViewState) {
        viewBinding.loadingSpinner.visibility = View.GONE
        viewBinding.message.visibility = View.GONE

        albumListViewAdapter.submitList(viewState.albums)
        viewBinding.albumList.visibility = View.VISIBLE
    }

    /** A listener triggered when users tap on an album in this view. */
    interface AlbumListViewListener {
        fun onClickAlbum(album: Album)
    }
}