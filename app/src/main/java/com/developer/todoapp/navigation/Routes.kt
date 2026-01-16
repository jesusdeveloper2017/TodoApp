package com.developer.todoapp.navigation

import kotlinx.serialization.Serializable

//Se usan para manejar las rutas de la navegación en las pantallas de la App

@Serializable
object HomeScreenRoute

/**
 * Para una pantalla que reciba parámetros
 * de otra pantalla
*/
@Serializable
data class TaskScreenRoute(

    /**
     * Si es null quiere decir que la tarea se va a crear
     * y si es diferente de null quiere decir que la tarea
     * se va a editar
    */
    val id:String? = null
)