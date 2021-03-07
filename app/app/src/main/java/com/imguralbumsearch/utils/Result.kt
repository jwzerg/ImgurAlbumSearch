package com.imguralbumsearch.utils

/** A helper class to wrap the status when we send request to fetch data. */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}