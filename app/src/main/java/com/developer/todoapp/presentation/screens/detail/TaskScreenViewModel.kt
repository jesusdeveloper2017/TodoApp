package com.developer.todoapp.presentation.screens.detail

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.developer.todoapp.data.FakeTaskLocalDataSource
import com.developer.todoapp.domain.Task
import com.developer.todoapp.navigation.TaskScreenRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Se usa savedStateHandle para obtener los parámetros que envía
 * HomeScreenRoot a TaskScreenRoot
*/
class TaskScreenViewModel(savedStateHandle:SavedStateHandle): ViewModel() {

    private val taskLocalDataSource = FakeTaskLocalDataSource

    var state by mutableStateOf(value = TaskScreenState())
        private set //Se coloca esto para que no se pueda modificar desde la vista

    //Channel es un flow caliente
    private val eventChannel = Channel<TaskScreenEvent>()
    val event = eventChannel.receiveAsFlow()

    //Se usa para obtener los parámetros que envía HomeScreenRoot a TaskScreenRoot
    val extra:TaskScreenRoute = savedStateHandle.toRoute<TaskScreenRoute>()

    //Se usa guardar los datos de la tarea que se obtiene dado el Id de la tarea
    private var editedTask:Task? = null

    //private val canSaveTask = snapshotFlow { state.title.text.toString() }

    init {

        getExtras()

        /*
        canSaveTask.onEach {

            state = state.copy(canSaveTask = it.isNotEmpty())

        }.launchIn(viewModelScope)
         */
    }

    private fun getExtras(){

        extra.id?.let { id -> //if(extra.id != null)

            viewModelScope.launch {

                //Se obtienen los datos de la tarea dado el Id de la tarea
                editedTask = taskLocalDataSource.getTaskById(id = id)

                editedTask?.let { //if(editedTask != null)

                    //Se cargan los datos obtenidos de la tarea en la pantalla
                    state = state.copy(title = TextFieldState(it.title),
                                       description = TextFieldState(it.description ?: ""),
                                       isCompleted = it.isCompleted, category = it.category)
                }
            }
        }
    }

    fun onAction(action:TaskScreenAction){

        viewModelScope.launch {

            Log.e("","TaskScreenViewModel.kt->onAction()->action: $action")

            when(action){

                //TaskScreenAction.OnBack -> {}  Se implementa directamente en TaskScreen

                //Se ejecuta cuando se marca o desmarca el CheckBox
                is TaskScreenAction.OnChangeTaskCompleted -> {

                    state = state.copy(isCompleted = action.isCompleted)

                }

                //Se ejecuta cuando se selecciona una categoría de la lista
                is TaskScreenAction.OnChangeTaskCategory -> {

                    state = state.copy(category = action.category)

                }

                //Se ejecuta cuando se presiona el botón "Guardar" ya sea para crear o editar una tarea
                TaskScreenAction.OnSaveTask -> {

                    //Si se cargaron los datos de la tarea
                    editedTask?.let { //if(editedTask != null)

                        //Se actualiza la tarea existente
                        taskLocalDataSource.updateTask(task = it.copy(id = it.id,
                                                                      title = state.title.text.toString(),
                                                                      description =  state.description.text.toString(),
                                                                      isCompleted = state.isCompleted,
                                                                      category = state.category))
                    }
                    //Sino, se cargaron los datos
                    ?: run { //else

                        //Se crea una nueva tarea
                        val task = Task(id = UUID.randomUUID().toString(),
                                        title = state.title.text.toString(), //Se obtiene el texto del TextField
                                        description = state.description.text.toString(), //Se obtiene el texto del TextField
                                        isCompleted = state.isCompleted,
                                        category = state.category)

                        taskLocalDataSource.addTask(task)
                    }

                    //Para que se ejecute el evento en la vista
                    eventChannel.send(element = TaskScreenEvent.OnTaskSaved)

                }
                else -> {
                    Log.e("","TaskScreenViewModel.kt->onAction()->NO se hace nada")
                }
            }
        }
    }
}