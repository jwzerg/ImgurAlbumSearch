package com.imguralbumsearch.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imguralbumsearch.rpc.ImgurSearchService
import com.imguralbumsearch.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val imgurSearchService: ImgurSearchService) :
    ViewModel() {

    private var lastSearchedQuery: String? = null
    private var searchJob: Job? = null

    private val albumListLiveData =
        MutableLiveData<Result<AlbumListViewState>>(
            Result.Success(AlbumListViewState(showAlbum = false))
        )

    fun getAlbumListLiveData(): LiveData<Result<AlbumListViewState>> = albumListLiveData


    fun searchAlbums(query: String) {
        // No need to search again when the query is the same.
        if (query == lastSearchedQuery) {
            return
        }

        lastSearchedQuery = query

        // Cancel the previous job.
        searchJob?.let {
            it.cancel()
        }

        // Update the UI to show a loading spinner.
        albumListLiveData.value = Result.Loading

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = imgurSearchService.searchAlbums(query)

                // Filter out albums without images.
                val albumList =
                    response.body()!!.albumList.filter { album -> album.images.isNotEmpty() }

                albumListLiveData.postValue(
                    Result.Success(AlbumListViewState(/* showAlbum= */true, albumList))
                )
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Fail to fetch albums!", e)
                albumListLiveData.postValue(Result.Error(e))
            }
        }
    }

    companion object {
        private val LOG_TAG: String? = HomeViewModel::class.simpleName
    }
}