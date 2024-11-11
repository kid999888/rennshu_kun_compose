package com.example.functional_verification.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.functional_verification.data.room.dao.base.BaseDao
import com.example.functional_verification.data.room.entity.MessageEntity

@Dao
interface MessageListDao : BaseDao<MessageEntity> {
    @Query("SELECT * FROM message_list")
    suspend fun getAll(): List<MessageEntity>

    @Query("SELECT COUNT(*) FROM message_list")
    suspend fun getCount(): Int
}