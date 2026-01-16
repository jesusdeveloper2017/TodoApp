package com.developer.todoapp.presentation.screens.detail

import com.developer.todoapp.domain.Category

/**
 * Se declaran todas las operaciones que se pueden hacer en la
 * pantalla de TaskScreen
*/
sealed interface TaskScreenAction {

    //Se ejecuta cuando se presiona el icono para ir a la pantalla de atrás
    data object OnBack:TaskScreenAction

    //Se ejecuta cuando se marca o desmarca el CheckBox
    data class OnChangeTaskCompleted(val isCompleted:Boolean):TaskScreenAction

    //Se ejecuta cuando se selecciona una categoría de la lista
    data class OnChangeTaskCategory(val category:Category?):TaskScreenAction

    //Se ejecuta cuando se presiona el botón "Guardar" ya sea para crear o editar una tarea
    data object OnSaveTask:TaskScreenAction

}