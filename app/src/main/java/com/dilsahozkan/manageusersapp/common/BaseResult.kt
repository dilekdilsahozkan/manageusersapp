package com.dilsahozkan.manageusersapp.common

sealed class BaseResult <out T : Any> {
    data class Success <T: Any>(val data : T, val responseCode: Int? = null, val message: String? = null) : BaseResult<T>()
    data class Error <T : Any>(val rawResponse: T) : BaseResult<T>()
}