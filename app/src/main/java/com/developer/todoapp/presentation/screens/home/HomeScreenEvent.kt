package com.developer.todoapp.presentation.screens.home

/**
 * Se declaran todos los eventos que se pueden lanzar desde
 * HomeScreenViewModel hacia la pantalla de HomeScreen
*/
sealed class HomeScreenEvent {

    data object DeleteAllTasks:HomeScreenEvent()

    data object DeleteTask:HomeScreenEvent()

    data object UpdatedTask:HomeScreenEvent()

}