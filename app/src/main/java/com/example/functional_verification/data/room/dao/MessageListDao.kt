package com.example.functional_verification.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.functional_verification.data.room.dao.base.BaseDao
import com.example.functional_verification.data.room.entity.MessageListEntity

@Dao
interface MessageListDao : BaseDao<MessageListEntity> {
    @Query("SELECT * FROM message_list")
    suspend fun getAll(): List<MessageListEntity>

    @Query("SELECT COUNT(*) FROM message_list")
    suspend fun getCount(): Int
}