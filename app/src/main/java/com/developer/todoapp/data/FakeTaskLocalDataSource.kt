package com.developer.todoapp.data

import com.developer.todoapp.domain.Task
import com.developer.todoapp.domain.TaskLocalDataSource
import com.developer.todoapp.presentation.screens.home.providers.completedTasks
import com.developer.todoapp.presentation.screens.home.providers.incompletedTasks
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object FakeTaskLocalDataSource: TaskLocalDataSource {

    private val _taskFlow = MutableStateFlow<List<Task>>(emptyList())

    init {
        //Se llena la lista de tareas con datos de prueba
        _taskFlow.value = completedTasks + incompletedTasks
    }

    override val taskFlow: Flow<List<Task>>
        get() = _taskFlow

    override suspend fun addTask(task: Task) {

        val tasks = _taskFlow.value.toMutableList()
        tasks.add(task)

        //Simula un tiempo de espera
        delay(100)

        _taskFlow.value = tasks
    }

    override suspend fun updateTask(task:Task) {

        val tasks = _taskFlow.value.toMutableList()

        val taskIndex:Int = tasks.indexOfFirst { it.id == task.id }

        if(taskIndex != -1){

            tasks[taskIndex] = task

            //Simula un tiempo de espera
            delay(100)

            _taskFlow.value = tasks

        }
    }

    override suspend fun removeTask(task:Task) {

        val tasks = _taskFlow.value.toMutableList()
        tasks.remove(task)

        //Simula un tiempo de espera
        delay(100)

        _taskFlow.value = tasks
    }

    override suspend fun deleteAllTasks() {
        _taskFlow.value = emptyList()
    }

    override suspend fun getTaskById(id:String): Task? {

        return _taskFlow.value.firstOrNull { it.id == id }


    }

}