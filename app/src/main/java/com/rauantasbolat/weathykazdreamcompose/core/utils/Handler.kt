package com.rauantasbolat.weathykazdreamcompose.core.utils

sealed class Handler<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Handler<T>(data)
    class Error<T>(message: String, data: T? = null) : Handler<T>(data, message)
}