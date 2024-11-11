package com.example.functional_verification.data.repository

import com.example.functional_verification.data.room.dao.MessageListDao
import com.example.functional_verification.data.room.entity.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(
    private val messageListDao: MessageListDao,
) {

    suspend fun getAll(): List<MessageEntity> {
        return withContext(Dispatchers.IO) {
            messageListDao.getAll()
        }
    }
}