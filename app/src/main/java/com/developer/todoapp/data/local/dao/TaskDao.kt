package com.developer.todoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.developer.todoapp.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getTaskById(id:String): TaskEntity?

    @Upsert
    suspend fun upsertTask(taskEntity: TaskEntity)

    @Query("DELETE FROM TaskEntity WHERE id = :id")
    suspend fun deleteTaskById(id: String)

    @Query("DELETE FROM TaskEntity")
    suspend fun deleteAllTasks()

}