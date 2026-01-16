package com.developer.todoapp.presentation.screens.home.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.developer.todoapp.domain.Category
import com.developer.todoapp.domain.Task
import com.developer.todoapp.presentation.screens.home.HomeScreenState

/**
 * Se usa para colocar datos de prueba en el preview de la vista HomeScreen
*/
class HomeScreenPreviewProvider: PreviewParameterProvider<HomeScreenState> {

    override val values: Sequence<HomeScreenState>
        get() = sequenceOf(
            HomeScreenState(
                date = "March 9, 2024",
                summary = "5 incompletas, 5 completadas",
                completedTasks = completedTasks,
                incompletedTasks = incompletedTasks
            )
        )
}

val completedTasks = mutableListOf<Task>()
    .apply {
        repeat(20){
            add(
                Task(
                    id = it.toString(),
                    title = "Task $it",
                    description = "Description $it",
                    category = Category.WORK,
                    isCompleted = true
                )
            )
        }
}

val incompletedTasks = mutableListOf<Task>()
    .apply {
        repeat(20){
            add(
                Task(
                    id = (it+30).toString(),
                    title = "Task $it",
                    description = "Description $it",
                    category = Category.OTHER,
                    isCompleted = false
                )
            )
        }
}