package com.developer.todoapp.presentation.screens.detail

import androidx.compose.foundation.text.input.TextFieldState
import com.developer.todoapp.domain.Category

/**
 * Se usa para guardar los datos que se muestran o se actualizan,
 * o se usan en la pantalla de TaskScreen
*/
data class TaskScreenState(
    val title:TextFieldState = TextFieldState(),
    val description:TextFieldState = TextFieldState(),
    val isCompleted:Boolean = false,
    val category:Category? = null,
    //val canSaveTask: Boolean = false
)