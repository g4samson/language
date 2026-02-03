package com.profs.languageapp.data.model

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    object NoInternet : NetworkResult<Nothing>()
    data class ServerError(val message: String) : NetworkResult<Nothing>()
}