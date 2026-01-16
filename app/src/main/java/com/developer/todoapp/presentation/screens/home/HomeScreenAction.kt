package com.developer.todoapp.presentation.screens.home

import com.developer.todoapp.domain.Task

/**
 * Se declaran todas las operaciones que se pueden hacer en la
 * pantalla de HomeScreen
*/
sealed interface HomeScreenAction {

    //Se ejecuta cuando se presiona la opción "Borrar todo" del Toolbar
    data object OnDeleteAllTasks:HomeScreenAction

    //Se ejecuta cuando se presiona el icono para borrar la tarea
    data class OnDeleteTask(val task:Task):HomeScreenAction

    //Se ejecuta cuando se marca/desmarca el checkbox de la tarea
    data class OnToggleTask(val task:Task):HomeScreenAction

    //Se ejecuta cuando se selecciona alguna tarea de la lista
    data class OnEditTask(val id:String):HomeScreenAction

    //Se ejecuta cuando se presiona el botón para crear una tarea
    data object OnAddTask:HomeScreenAction

}