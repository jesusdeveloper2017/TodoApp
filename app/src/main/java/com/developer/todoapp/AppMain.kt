package com.developer.todoapp

import android.app.Application
import com.developer.todoapp.data.local.DataSourceFactory
import com.developer.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Se usa esta clase para proveer los parámetros necesarios
 * a los viewmodels de la aplicación (Es decir hacer la inyección
 * de dependencias de forma manual)
*/
class AppMain: Application() {

    private val dispatcherIO:CoroutineDispatcher
        get() = Dispatchers.IO

    //Se usa para proveer el dataSource a los viewmodels
    val dataSource:TaskLocalDataSource
        get() = DataSourceFactory.createDataSource(context = this, dispatcherIO)

}