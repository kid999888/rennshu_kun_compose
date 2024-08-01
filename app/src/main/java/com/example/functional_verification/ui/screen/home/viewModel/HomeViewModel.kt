package com.example.functional_verification.ui.screen.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.functional_verification.data.repository.HomeRepository

class HomeViewModel(
    private val repository: HomeRepository,
) : ViewModel() {
    private val _messageList = MutableLiveData<String>()
    val messageList: LiveData<String> get() = _messageList

}