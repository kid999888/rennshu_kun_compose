/**
 * File Name: BaseViewModel.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2025/04/10
 * Copyright: © 2025 Gyoushin Ou. All rights reserved.
 * Description: BaseViewModel
 */

package com.example.rennshukun_compose.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


open class BaseViewModel : ViewModel() {
    private val _toastMsg = MutableStateFlow<String>("")
    val toastMsg = _toastMsg.asStateFlow()

    // CoroutineExceptionHandlerを定義
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is HttpException -> {
                // HttpExceptionをhandle
                handleHttpException(exception)
            }

            is IOException -> {
                // ネットワークエラーをhandle
                handleNetworkException(exception)
            }

            else -> {
                // それ以外をhandle
                handleGenericException(exception)
            }
        }
    }

    // CoroutineScope をカスタマイズ
    private val viewModelScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main + exceptionHandler)

    // handleしたい場合はこちらを使う
    fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }

    // retrofit2のHttpExceptionをhandle
    private fun handleHttpException(exception: HttpException) {
        viewModelScope.launch {
            val msg = "HTTP error: ${exception.code()}"
            Log.d("ExceptionHandle", msg)
            _toastMsg.emit(msg)
        }
    }

    // ネットワークExceptionをhandle
    private fun handleNetworkException(exception: IOException) {
        viewModelScope.launch {
            val msg = "Network error: ${exception.message}"
            Log.d("ExceptionHandle", msg)
            _toastMsg.emit(msg)
        }
    }

    // それ以外のExceptionをhandle
    private fun handleGenericException(exception: Throwable) {
        viewModelScope.launch {
            val msg = "Unexpected error: ${exception.message}"
            Log.d("ExceptionHandle", msg)
            _toastMsg.emit(msg)
        }
    }

    fun postToastMsg(msg: String) {
        viewModelScope.launch {
            _toastMsg.emit(msg)
        }
    }

    fun clearToastMsg() {
        viewModelScope.launch {
            _toastMsg.emit("")
        }
    }

}