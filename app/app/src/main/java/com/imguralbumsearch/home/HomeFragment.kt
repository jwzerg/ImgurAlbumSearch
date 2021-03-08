package com.imguralbumsearch.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.imguralbumsearch.album.AlbumActivity
import com.imguralbumsearch.databinding.HomeFragmentBinding
import com.imguralbumsearch.rpc.Album
import dagger.hilt.android.AndroidEntryPoint
import com.imguralbumsearch.utils.Result

@AndroidEntryPoint
class HomeFragment : Fragment(), AlbumListView.AlbumListViewListener {

    private lateinit var fragmentViewBinding: HomeFragmentBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentViewBinding =
            HomeFragmentBinding.inflate(inflater, container, /* attachToParent= */false)

        setupSearchBox()
        setupAlbumListView()

        return fragmentViewBinding.root
    }

    override fun onClickAlbum(album: Album) {
        val intent = AlbumActivity.createIntent(requireContext(), album)
        startActivity(intent)
    }

    private fun setupAlbumListView() {
        viewModel.getAlbumListLiveData()
            .observe(viewLifecycleOwner, Observer<Result<AlbumListViewState>> { viewState ->
                fragmentViewBinding.albumListView.bindView(viewState, this@HomeFragment)
            })
    }

    private fun setupSearchBox() {
        fragmentViewBinding.searchBox.editText?.setOnEditorActionListener { editText, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    performSearchAlbum(editText.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun performSearchAlbum(query: String) {
        // Hide IME before performing the search.
        fragmentViewBinding.searchBox.clearFocus()
        val inputMethodManager =
            requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            fragmentViewBinding.searchBox.windowToken, /* flags= */0
        )

        viewModel.searchAlbums(query)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}