package com.example.rennshukun_compose.data.repository

import com.example.rennshukun_compose.data.room.dao.MessageListDao
import com.example.rennshukun_compose.data.room.entity.MessageEntity
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