package com.developer.todoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

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
class AppModule {

    /**
     * Retorna el dispatcher IO de corrutinas
    */
    @Provides
    @Singleton
    fun providesDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

}