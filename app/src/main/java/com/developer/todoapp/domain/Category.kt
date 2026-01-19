package com.developer.todoapp.domain

//Se usa para definir las categorías que puede tener la tarea
enum class Category {
    WORK,
    PERSONAL,
    SHOPPING,
    OTHER;

    companion object {

        //Se usa para obtener la categoría a partir del ordinal
        fun fromOrdinal(ordinal:Int?): Category? {

            return entries.find { it.ordinal == ordinal }
        }

    }
}