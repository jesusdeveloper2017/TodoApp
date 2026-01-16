package com.developer.todoapp.presentation.screens.detail

/**
 * Se declaran todos los eventos que se pueden lanzar desde
 * TaskScreenViewModel hacia la pantalla de TaskScreen
*/
sealed interface TaskScreenEvent{

    data object OnTaskSaved:TaskScreenEvent

}