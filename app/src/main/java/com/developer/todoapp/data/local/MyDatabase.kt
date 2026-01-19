package com.developer.todoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developer.todoapp.data.local.dao.TaskDao
import com.developer.todoapp.data.local.entities.TaskEntity

@Database(entities = [TaskEntity::class],
          version = 1,
          exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    //Se usa para acceder al dao con las operaciones SQL
    abstract val taskDao:TaskDao

    companion object {

        @Volatile
        private var INSTANCE:MyDatabase? = null

        //Retorna la instancia de la base de datos
        fun getDatabase(context:Context): MyDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(context.applicationContext,
                                             MyDatabase::class.java,
                                            "task_database").build()
                INSTANCE = instance

                instance
            }
        }
    }
}