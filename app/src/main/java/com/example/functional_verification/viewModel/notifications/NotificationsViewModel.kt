/**
 * File Name: NotificationsViewModel.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2024/11/22
 * Copyright: © 2024 Gyoushin Ou. All rights reserved.
 * Description:
 */

package com.example.functional_verification.viewModel.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel() : ViewModel() {
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun setMessage(msg: String) {
        viewModelScope.launch {
            _message.emit(msg)
        }
    }

    fun clearMessage() {
        viewModelScope.launch {
            _message.emit(null)
        }

    }
}