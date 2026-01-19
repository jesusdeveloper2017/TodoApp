package com.developer.todoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Se utiliza la anotación HiltAndroidApp para indicar
 * que la App usa inyección de dependencias
 *
 * Esta clase debe ir en el Manifest.xml:
 *
 *  android:name=".AppMain"
*/
@HiltAndroidApp
class AppMain: Application()