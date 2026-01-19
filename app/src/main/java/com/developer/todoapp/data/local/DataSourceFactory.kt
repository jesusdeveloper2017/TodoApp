package com.developer.todoapp.data.local

import android.content.Context
import com.developer.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher

object DataSourceFactory {

    /**
     * Retorna una instancia de la clase RoomTaskLocalDataSource que hace
     * uso de las funciones TaskDao (base de datos local) y que a su vez
     * implementa la interface TaskLocalDataSource
    */
    fun createDataSource(context:Context,
                         dispatcher:CoroutineDispatcher): TaskLocalDataSource{

        val database = MyDatabase.getDatabase(context = context)

        return RoomTaskLocalDataSource(taskDao = database.taskDao,
                                       dispatcher = dispatcher)
    }
}