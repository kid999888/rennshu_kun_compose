package com.example.functional_verification.data.room.dao.base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entities: T): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<T>): List<Long>

    @Update
    suspend fun update(entity: T): Int

    @Update
    suspend fun update(vararg entities: T): Int

    @Update
    suspend fun update(entities: List<T>): Int

    @Delete
    suspend fun delete(entity: T): Int

    @Delete
    suspend fun delete(vararg entities: T): Int

    @Delete
    suspend fun delete(entities: List<T>): Int
}