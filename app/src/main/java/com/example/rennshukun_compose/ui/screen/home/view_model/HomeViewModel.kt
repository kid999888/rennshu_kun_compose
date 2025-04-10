package com.example.rennshukun_compose.ui.screen.home.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rennshukun_compose.data.repository.HomeRepository
import com.example.rennshukun_compose.data.room.entity.asDomainModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
) : ViewModel() {
    private val _messageList = MutableLiveData<List<String>>()
    val messageList: LiveData<List<String>> get() = _messageList

    fun getAll() {
        viewModelScope.launch {
            val result = repository.getAll()
            _messageList.postValue(result.asDomainModel())
        }
    }
}