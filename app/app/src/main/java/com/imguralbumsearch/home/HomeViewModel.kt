package com.imguralbumsearch.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imguralbumsearch.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val albumListLiveData =
        MutableLiveData<Result<AlbumListViewState>>(
            Result.Success(AlbumListViewState(showAlbum = false))
        )

    fun getAlbumListLiveData(): LiveData<Result<AlbumListViewState>> = albumListLiveData
}