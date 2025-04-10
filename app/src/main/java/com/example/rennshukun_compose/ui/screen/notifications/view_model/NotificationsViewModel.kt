/**
 * File Name: NotificationsViewModel.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2024/11/22
 * Copyright: © 2024 Gyoushin Ou. All rights reserved.
 * Description:
 */

package com.example.rennshukun_compose.ui.screen.notifications.view_model

import androidx.lifecycle.viewModelScope
import com.example.rennshukun_compose.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel() : BaseViewModel() {
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