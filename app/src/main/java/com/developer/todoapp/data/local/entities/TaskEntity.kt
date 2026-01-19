package com.developer.todoapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Tabla de Room para guardar los datos de la tarea
@Entity
data class TaskEntity(

    @PrimaryKey(autoGenerate = false)
    val id:String,
    val title:String,
    val description:String?,
    val isCompleted:Boolean,
    val category:Int? = null,
    val date:Long
)