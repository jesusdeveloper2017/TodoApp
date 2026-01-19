package com.developer.todoapp.data.di

import android.content.Context
import androidx.room.Room
import com.developer.todoapp.data.local.MyDatabase
import com.developer.todoapp.data.local.RoomTaskLocalDataSource
import com.developer.todoapp.data.local.dao.TaskDao
import com.developer.todoapp.domain.TaskLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

//Parámetros o dependencias de la capa de datos
/**
 * Se usa esta clase para evitar tener muchas instancias creadas
 * de diferentes clases, en diferentes partes del proyecto
*/

/**
 * Se usa @InstallIn(SingletonComponent::class) y
 *  @Singleton
 *  para indicar que las instancias o "dependencias"
 *  deben vivir toda la app, y se cree una sola vez
*/
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    /**
     * Retorna la instancia de base de datos de Room de la App
    */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context): MyDatabase{

        return Room.databaseBuilder(context.applicationContext,
                             MyDatabase::class.java,
                            "task_database").build()
    }

    /**
     * Retorna la instancia de TaskDao de la base de datos de Room
    */
    @Provides
    @Singleton
    fun provideTaskDao(database:MyDatabase): TaskDao = database.taskDao

    /**
     * Retorna la instancia de TaskLocalDataSource que usan los viewmodels
    */
    @Provides
    @Singleton
    fun provideTaskLocalDataSource(taskDao:TaskDao,
                                   dispatcher:CoroutineDispatcher): TaskLocalDataSource{

        return RoomTaskLocalDataSource(taskDao, dispatcher)
    }

}