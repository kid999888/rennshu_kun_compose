package com.example.functional_verification.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_list")
data class MessageListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val message: String,
)
