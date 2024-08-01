package com.example.functional_verification.data.repository

import com.example.functional_verification.data.room.dao.MessageListDao
import com.example.functional_verification.data.room.entity.MessageListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(
    private val messageListDao: MessageListDao,
) {

    suspend fun getAll(): List<MessageListEntity> {
        return withContext(Dispatchers.IO) {
            messageListDao.getAll()
        }
    }
}