package com.imguralbumsearch.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imguralbumsearch.databinding.AlbumFragmentBinding
import com.imguralbumsearch.rpc.Album

/** The fragment that displays all photos in an album. */
class AlbumFragment : Fragment() {

    private lateinit var fragmentViewBinding: AlbumFragmentBinding
    private lateinit var albumPhotoListAdapter: AlbumPhotoListAdapter
    private lateinit var album: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        album = requireArguments().get(ARG_ALBUM) as Album
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentViewBinding =
            AlbumFragmentBinding.inflate(inflater, container, /* attachToParent= */false)

        albumPhotoListAdapter = AlbumPhotoListAdapter(album.images)
        fragmentViewBinding.photoList.apply {
            adapter = albumPhotoListAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL, /* reverseLayout= */
                false
            )
        }

        return fragmentViewBinding.root
    }

    companion object {
        private const val ARG_ALBUM = "arg_album"

        fun newInstance(album: Album) = AlbumFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_ALBUM, album)
            }
        }
    }
}