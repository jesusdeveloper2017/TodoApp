package com.developer.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developer.todoapp.data.local.dao.TaskDao
import com.developer.todoapp.data.local.entities.TaskEntity

@Database(entities = [TaskEntity::class],
          version = 1,
          exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    //Se usa para acceder al dao con las operaciones SQL
    abstract val taskDao:TaskDao

}