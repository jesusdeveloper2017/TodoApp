@file:OptIn(ExperimentalMaterial3Api::class)

package com.developer.todoapp.presentation.screens.home

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.developer.todoapp.R
import com.developer.todoapp.presentation.screens.home.components.SectionTitle
import com.developer.todoapp.presentation.screens.home.components.SummaryInfo
import com.developer.todoapp.presentation.screens.home.components.TaskItem
import com.developer.todoapp.presentation.screens.home.providers.HomeScreenPreviewProvider
import com.developer.todoapp.ui.theme.TodoAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreenRoot(navigateToTaskScreen:(id:String?)-> Unit){

    val viewModel = viewModel<HomeScreenViewModel>()
    val state:HomeScreenState = viewModel.state
    val event:Flow<HomeScreenEvent> = viewModel.event

    val context:Context = LocalContext.current

    /**
     * Se usa LaunchedEffect para que el evento se ejecute solo 1 vez
     * y no cada vez que se recompone la pantalla
    */
    LaunchedEffect(key1 = true) {

        //Captura los eventos lanzados en HomeScreenViewModel
        event.collect { event ->

            //Se evalua el evento que se capturó
            when(event){

                HomeScreenEvent.DeleteAllTasks -> {

                    Toast.makeText(context,
                            context.resources.getString(R.string.all_tasks_deleted),
                         Toast.LENGTH_SHORT).show()

                }
                HomeScreenEvent.DeleteTask -> {

                    Toast.makeText(context,
                            context.resources.getString(R.string.task_deleted),
                         Toast.LENGTH_SHORT).show()

                }
                HomeScreenEvent.UpdatedTask -> {

                    Toast.makeText(context,
                            context.resources.getString(R.string.task_updated),
                         Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    HomeScreen(state = state,
               onAction = { action ->

                   when(action){

                       //Se ejecuta cuando se selecciona alguna tarea de la lista
                       is HomeScreenAction.OnEditTask ->{

                           //Se le pasa el id de la tarea existente para editarla
                           navigateToTaskScreen(action.id)

                       }
                       //Se ejecuta cuando se presiona el botón para crear una tarea
                       HomeScreenAction.OnAddTask -> {

                           //Se le pasa null para que se cree una nueva tarea
                           navigateToTaskScreen(null)

                       }
                       //Sino, es otra acción del archivo HomeScreenAction
                       else -> {

                           viewModel.onAction(action = action)

                       }
                   }
               }
    )
}

@Composable
fun HomeScreen(modifier:Modifier = Modifier,
               state:HomeScreenState,
               onAction:(HomeScreenAction)-> Unit){

    var isMenuExpanded:Boolean by remember { mutableStateOf(value = false) }

    Scaffold(modifier = modifier.fillMaxSize(),
             topBar = {

                 TopAppBar(

                     title = {

                         Text(text = stringResource(R.string.app_name),
                              color = MaterialTheme.colorScheme.onSurface,
                              fontWeight = FontWeight.Bold
                         )

                    },
                    actions = {

                        Box(modifier = Modifier
                                       .padding(8.dp)
                                       .clickable {

                                           //Para que se muestre la lista de opciones
                                           isMenuExpanded = true

                                       }
                        ) {

                            Icon(imageVector = Icons.Default.MoreVert,
                                 contentDescription = "Menú",
                                 tint = MaterialTheme.colorScheme.onSurface
                            )

                            DropdownMenu(expanded = isMenuExpanded,
                                         onDismissRequest = {
                                             isMenuExpanded = false
                                         }
                            ) {

                                DropdownMenuItem(onClick = {

                                    onAction(HomeScreenAction.OnDeleteAllTasks)
                                    isMenuExpanded = false

                                },
                                    text = {

                                        Text(text = stringResource(R.string.delete_all))

                                    }
                                )
                            }
                        }
                    }
                 )
            },
            floatingActionButton = {

                FloatingActionButton(onClick = {

                    Log.e("","HomeScreen.kt->HomeScreen()->FloatingActionButton->onAction(HomeScreenAction.OnAddTask)")
                    onAction(HomeScreenAction.OnAddTask)

                }) {

                    Icon(imageVector = Icons.Default.Add,
                         contentDescription = "Add Task",
                         tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
    ) { paddingValues ->

        //Si NO hay tareas ni completadas ni incompletas
        if (state.completedTasks.isEmpty() && state.incompletedTasks.isEmpty()) {

            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center) {

                //Se muestra un Texto
                Text(
                    text = stringResource(R.string.no_tasks),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )

            }
        }
        else{

                //Se usa LazyColumn para elementos que se cargan dinámicamente
                LazyColumn(modifier = Modifier.padding(paddingValues)
                                      .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)) {

                    item {

                        SummaryInfo(date = state.date,
                                    taskSummary = state.summary,
                                    completedTasks = state.completedTasks.size,
                                    totalTasks = (state.completedTasks.size + state.incompletedTasks.size)
                        )
                    }

                    //"Completadas"
                    //Se usa stickyHeader para que el título quede fijado aunque se haga scroll
                    stickyHeader {
                        SectionTitle(modifier = Modifier.fillParentMaxWidth()
                                                .background(color = MaterialTheme.colorScheme.surface),
                                    title = stringResource(R.string.complete_tasks)
                        )
                    }

                    items(items = state.completedTasks,
                          key = { task -> task.id }) { task ->

                        TaskItem(modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)),
                                 task = task,
                                 onClickItem = {

                                     //Se le pasa el id de la tarea existente para editarla
                                     onAction(HomeScreenAction.OnEditTask(task.id))

                                 },
                                 onDeleteItem = {

                                     onAction(HomeScreenAction.OnDeleteTask(task))

                                 },
                                 onToggleCompletion = {

                                     onAction(HomeScreenAction.OnToggleTask(task))

                                 }
                        )
                    }

                    //"Incompletas"
                    //Se usa stickyHeader para que el título quede fijado aunque se haga scroll
                    stickyHeader {
                        SectionTitle(modifier = Modifier.fillParentMaxWidth()
                                                .background(color = MaterialTheme.colorScheme.surface),
                                    title = stringResource(R.string.incomplete_tasks)
                        )
                    }

                    items(items = state.incompletedTasks,
                          key = { task -> task.id }) { task ->

                        TaskItem(modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)),
                                 task = task,
                                 onClickItem = {

                                     //Se le pasa el id de la tarea existente para editarla
                                     onAction(HomeScreenAction.OnEditTask(task.id))

                                 },
                                 onDeleteItem = {
                                     onAction(HomeScreenAction.OnDeleteTask(task))
                                 },
                                 onToggleCompletion = {
                                     onAction(HomeScreenAction.OnToggleTask(task))
                                 }
                        )
                    }
                }
        }
    }
}

//Preview para modo claro
@Preview
@Composable
fun HomeScreenPreviewLight(
    @PreviewParameter(HomeScreenPreviewProvider::class) state: HomeScreenState) {

    TodoAppTheme {
        HomeScreen(
            state = HomeScreenState(
                date = state.date,
                summary = state.summary,
                completedTasks = state.completedTasks,
                incompletedTasks =  state.incompletedTasks
            ),
            onAction = {}
        )
    }
}

//Preview para modo oscuro
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDark(
    @PreviewParameter(HomeScreenPreviewProvider::class) state: HomeScreenState) {

    TodoAppTheme {
        HomeScreen(
            state = HomeScreenState(date = state.date,
                                  summary = state.summary,
                                  completedTasks = state.completedTasks,
                                  incompletedTasks = state.incompletedTasks
            ),
            onAction = { }
        )
    }
}