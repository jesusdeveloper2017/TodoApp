package com.developer.todoapp.presentation.screens.detail.providers

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.developer.todoapp.domain.Category
import com.developer.todoapp.presentation.screens.detail.TaskScreenState

/**
 * Se usa para colocar datos de prueba en el preview de la vista TaskScreen
*/
class TaskScreenStatePreviewProvider: PreviewParameterProvider<TaskScreenState> {

    override val values: Sequence<TaskScreenState>
        get() = sequenceOf(
            TaskScreenState(
                title = TextFieldState("Task 1"),
                description = TextFieldState("Description 1"),
                isCompleted = false,
                category = Category.WORK
            ),
            TaskScreenState(
                title = TextFieldState("Task 2"),
                description = TextFieldState("Description 2"),
                isCompleted = true,
                category = Category.WORK
            ),
            TaskScreenState(
                title = TextFieldState("Task 3"),
                description = TextFieldState("Description 3"),
                isCompleted = false,
                category = Category.OTHER
            ),
            TaskScreenState(
                title = TextFieldState("Task 4"),
                description = TextFieldState("Description 4"),
                isCompleted = true,
                category = null
            ),
            TaskScreenState(
                title = TextFieldState("Task 5"),
                description = TextFieldState(""),
                isCompleted = false,
                category = null
            )
        )
}