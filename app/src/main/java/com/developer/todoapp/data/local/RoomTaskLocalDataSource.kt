package com.developer.todoapp.data.local

import com.developer.todoapp.data.local.dao.TaskDao
import com.developer.todoapp.data.mapper.toTask
import com.developer.todoapp.data.mapper.toTaskEntity
import com.developer.todoapp.domain.Task
import com.developer.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Clase implementadora de la interface TaskLocalDataSource.kt
*/
class RoomTaskLocalDataSource(private val taskDao:TaskDao,
                              private val dispatcher:CoroutineDispatcher = Dispatchers.IO): TaskLocalDataSource {


    override val taskFlow:Flow<List<Task>>
        get() = taskDao.getAllTasks().map { taskEntityList ->

            taskEntityList.map { it.toTask() }
        }

    override suspend fun addTask(task:Task) = withContext(dispatcher){

        taskDao.upsertTask(taskEntity = task.toTaskEntity())

    }

    override suspend fun updateTask(task:Task) = withContext(dispatcher){

        taskDao.upsertTask(taskEntity = task.toTaskEntity())

    }

    override suspend fun removeTask(task:Task) = withContext(dispatcher){

       taskDao.deleteTaskById(id = task.id)

    }

    override suspend fun deleteAllTasks() = withContext(dispatcher){

        taskDao.deleteAllTasks()

    }

    override suspend fun getTaskById(id: String): Task? = withContext(dispatcher){

        taskDao.getTaskById(id = id)?.toTask()
    }
}