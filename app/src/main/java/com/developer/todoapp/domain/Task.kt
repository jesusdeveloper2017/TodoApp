package com.developer.todoapp.domain

import java.time.LocalDateTime

//Se usa para guardar los datos de la tarea que a su vez se guardan en RAM
data class Task(
    val id:String,
    val title:String,
    val description:String?,
    val isCompleted:Boolean = false,
    val category:Category? = null,
    val date:LocalDateTime = LocalDateTime.now()
)
