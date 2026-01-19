package com.developer.todoapp.data.mapper

import com.developer.todoapp.data.local.entities.TaskEntity
import com.developer.todoapp.domain.Task
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Convierte un objeto del modelo de dominio
 * a un objeto del modelo de la capa de datos
*/
fun Task.toTaskEntity(): TaskEntity{

    //Se convierte la fecha de LocalDateTime a Long para guardarla en Room
    val date:Long = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    return TaskEntity(id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        date = date)
}

/**
 * Convierte un objeto del modelo de la capa de datos
 * a un objeto del modelo de dominio
*/
fun TaskEntity.toTask(): Task {

    //Se convierte la fecha de Long a LocalDateTime para mostrarla en la vista
    val date:LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(date),
                                ZoneId.systemDefault())
    return Task(id = id,
                title = title,
                description = description,
                isCompleted = isCompleted,
                date = date)
}