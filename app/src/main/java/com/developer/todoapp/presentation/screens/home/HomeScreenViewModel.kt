package com.developer.todoapp.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.developer.todoapp.AppMain
import com.developer.todoapp.data.FakeTaskLocalDataSource
import com.developer.todoapp.domain.Task
import com.developer.todoapp.domain.TaskLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Se usan @HiltViewModel e @Inject constructor() para indicar que los parámetros
 * que se reciben los provee Hilt y que deben estar declarados en algún módulo
*/
@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val taskLocalDataSource:TaskLocalDataSource): ViewModel() {
    //private val taskLocalDataSource = FakeTaskLocalDataSource

    var state by mutableStateOf(HomeScreenState())
        private set //Se coloca esto para que no se pueda modificar desde la vista

    //Channel es un flow caliente
    private val eventChannel = Channel<HomeScreenEvent>()
    val event = eventChannel.receiveAsFlow()

    init {

        //Se obtiene/actualiza la fecha y hora del dispositivo
        state = state.copy(date = LocalDate.now().let {
            DateTimeFormatter.ofPattern("EEEE, MMMM dd yyy").format(it)
        })

        //Se obtienen todas las tareas guardadas
        taskLocalDataSource.taskFlow
            .onEach {

                //Se obtienen las tareas completadas
                val completedTasks:List<Task> = it.filter {
                    task-> task.isCompleted
                }.sortedByDescending { task -> task.date } //Para ordenar de la fecha más reciente a la más antigua

                //Se obtienen las tareas incompletas
                val incompletedTasks:List<Task> = it.filter {
                    task-> !task.isCompleted
                }.sortedByDescending { task -> task.date } //Para ordenar de la fecha más reciente a la más antigua

                //Se actualizan las tareas guardadas en el estado de la pantalla
                state = state.copy(summary = "${incompletedTasks.size} incompletas, ${completedTasks.size} completadas",
                                   completedTasks = completedTasks,
                                   incompletedTasks = incompletedTasks)
            }
            .launchIn(viewModelScope)
    }

    /**
     * Se ejecuta la acción que corresponda
    */
    fun onAction(action:HomeScreenAction){

        viewModelScope.launch {

            Log.e("","HomeScreenViewModel.kt->onAction()->action: $action")

            when(action){

                //Se ejecuta cuando se presiona la opción "Borrar todo" del Toolbar
                HomeScreenAction.OnDeleteAllTasks->{

                    Log.e("","HomeScreenViewModel.kt->onAction()->Se borran todas las tareas")
                    taskLocalDataSource.deleteAllTasks()

                    //Para que se ejecute el evento en la vista
                    eventChannel.send(element = HomeScreenEvent.DeleteAllTasks)
                }

                //Se ejecuta cuando se presiona el icono para borrar la tarea
                is HomeScreenAction.OnDeleteTask ->{

                    Log.e("","HomeScreenViewModel.kt->onAction()->Se borra la tarea seleccionada")
                    taskLocalDataSource.removeTask(action.task)

                    //Para que se ejecute el evento en la vista
                    eventChannel.send(element = HomeScreenEvent.DeleteTask)
                }

                //Se ejecuta cuando se marca/desmarca el checkbox de la tarea
                is HomeScreenAction.OnToggleTask ->{

                    Log.e("","HomeScreenViewModel.kt->onAction()->Se marca/desmarca el checkbox")
                    //Se obtiene la tarea y solo se actualiza su estado
                    val updatedTask:Task =
                        action.task.copy(isCompleted = !action.task.isCompleted)

                    //Se le pasa la tarea con el estado actualizado a la función
                    taskLocalDataSource.updateTask(updatedTask)

                    //Para que se ejecute el evento en la vista
                    eventChannel.send(element = HomeScreenEvent.UpdatedTask)
                }

                //is HomeScreenAction.OnEditTask -> {} Se implementa directamente en HomeScreen
                //HomeScreenAction.OnAddTask -> {} Se implementa directamente en HomeScreen
                else -> {
                    Log.e("","HomeScreenViewModel.kt->onAction()->NO se hace nada")
                }
            }
        }
    }
}