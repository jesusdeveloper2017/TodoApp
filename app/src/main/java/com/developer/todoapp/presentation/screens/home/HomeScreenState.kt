package com.developer.todoapp.presentation.screens.home

import com.developer.todoapp.domain.Task

/**
 * Se usa para guardar los datos que se muestran o se actualizan,
 * o se usan en la pantalla de HomeScreen
*/
data class HomeScreenState(
    val date:String = "",
    val summary:String = "",
    val completedTasks:List<Task> = emptyList(),
    val incompletedTasks:List<Task> = emptyList()
)