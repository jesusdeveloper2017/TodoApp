@file:OptIn(ExperimentalMaterial3Api::class)

package com.developer.todoapp.presentation.screens.detail

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.developer.todoapp.R
import com.developer.todoapp.domain.Category
import com.developer.todoapp.presentation.screens.detail.providers.TaskScreenStatePreviewProvider
import com.developer.todoapp.ui.theme.TodoAppTheme
import kotlinx.coroutines.flow.Flow

//Se recibe el viewmodel por parámetro
@Composable
fun TaskScreenRoot(backToHomeScreen:() -> Unit,
                   viewModel:TaskScreenViewModel){

    //val viewModel = viewModel<TaskScreenViewModel>()
    val state:TaskScreenState = viewModel.state
    val event:Flow<TaskScreenEvent> = viewModel.event

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

                TaskScreenEvent.OnTaskSaved -> {

                    //Se muestra el mensaje
                    Toast.makeText(context,
                            context.resources.getString(R.string.task_saved),
                         Toast.LENGTH_SHORT).show()

                    //Para regresar a la pantalla de HomeScreen
                    backToHomeScreen()

                }
            }
        }
    }

    TaskScreen(state = state,
               onAction = { action ->

                    when(action){

                        //Se ejecuta cuando se presiona el icono para ir a la pantalla de atrás
                        TaskScreenAction.OnBack -> {
                            backToHomeScreen()
                        }
                        //Sino, es otra acción del archivo TaskScreenAction
                        else -> {
                            viewModel.onAction(action = action)
                        }
                    }
               }
    )
}

@Composable
fun TaskScreen(modifier:Modifier = Modifier,
               state:TaskScreenState,
               onAction:(TaskScreenAction)-> Unit){

    var isExpanded:Boolean by remember { mutableStateOf(value = false) }
    var isDescriptionFocused:Boolean by remember { mutableStateOf(value = false) }

    Scaffold(topBar = {

                 TopAppBar(

                     title = {

                         Text(text = stringResource(R.string.task),
                              style = MaterialTheme.typography.headlineSmall)

                    },
                    navigationIcon = {

                        Icon(imageVector = Icons.Default.ArrowBackIosNew,
                             contentDescription = "Back",
                             tint = MaterialTheme.colorScheme.onSurface,
                             modifier = Modifier.clickable {

                                 onAction(TaskScreenAction.OnBack)

                             }
                         )
                    }
                 )
             }) { paddingValues ->

            Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                   modifier = modifier
                       .fillMaxSize()
                       .padding(paddingValues)
                       .padding(horizontal = 16.dp)
                       .imePadding() /*Para que esta vista muestre un padding adicional cuando se abra el teclado */) {

                Row(verticalAlignment = Alignment.CenterVertically){

                    Text(text = stringResource(R.string.completed),
                         modifier = Modifier.padding(8.dp))

                    Checkbox(checked = state.isCompleted,
                             onCheckedChange = {

                                 onAction(TaskScreenAction.OnChangeTaskCompleted(isCompleted = it))

                             }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {

                            //Para que se muestre la lista
                            isExpanded = true

                        }) {

                        Text(text = state.category?.toString() ?: stringResource(R.string.category),
                             style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                             modifier = Modifier
                                 .border(width = 1.dp,
                                         color = MaterialTheme.colorScheme.outline,
                                         shape = RoundedCornerShape(8.dp))
                                 .padding(8.dp)
                        )

                        Box(modifier = Modifier.padding(8.dp),
                            contentAlignment = Alignment.Center){

                            Icon(imageVector = Icons.Default.KeyboardArrowDown,
                                 contentDescription = "Arrow Down",
                                 tint = MaterialTheme.colorScheme.onSurface
                            )

                            DropdownMenu(expanded = isExpanded,
                                         onDismissRequest = { isExpanded = false },
                                         modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceContainerHighest)) {

                                Column{

                                    Category.entries.forEach { category ->

                                        Text(text = category.name,
                                             style = MaterialTheme.typography.bodyMedium.copy(
                                                 color = MaterialTheme.colorScheme.onSurface),
                                             modifier = Modifier
                                                 .padding(8.dp)
                                                 .clickable {

                                                     Log.e(
                                                         "",
                                                         "TaskScreen.kt->TaskScreen()->Category: $category"
                                                     )
                                                     onAction(
                                                         TaskScreenAction.OnChangeTaskCategory(
                                                             category
                                                         )
                                                     )

                                                     //Para que se muestre la lista
                                                     isExpanded = false

                                                 }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                BasicTextField(state = state.title,
                               textStyle = MaterialTheme.typography.headlineLarge.copy(
                                   color = MaterialTheme.colorScheme.onSurface
                               ),
                               cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary), //Se usa para cambiar el color del cursor
                               lineLimits = TextFieldLineLimits.SingleLine,
                               decorator = { innerBox ->

                                   Column(modifier = Modifier.fillMaxWidth()) {

                                       //Si la caja de texto está vacía
                                       if(state.title.text.toString().isEmpty()){

                                           //Se muestra un Text de placeholder
                                           Text(text = stringResource(R.string.task_title),
                                                modifier = Modifier.fillMaxWidth(),
                                                color = MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.5f),
                                                style = MaterialTheme.typography.headlineLarge.copy(
                                                    fontWeight = FontWeight.Bold)
                                           )
                                       }
                                       else{

                                           innerBox()

                                       }
                                   }
                               }
                )

                BasicTextField(state = state.description,
                               textStyle = MaterialTheme.typography.bodyLarge.copy(
                                   color = MaterialTheme.colorScheme.onSurface
                               ),
                               cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary), //Se usa para cambiar el color del cursor
                               //Se usa para limitar la altura de la caja de texto
                               lineLimits = /*Si tiene el foco*/if(isDescriptionFocused) {

                                   /**
                                    * Se coloca la cantidad mínima y máxima para
                                    * la caja de texto actual
                                   */
                                   TextFieldLineLimits.MultiLine(
                                       minHeightInLines = 1,
                                       maxHeightInLines = 5
                                   )
                               }
                                else{

                                    TextFieldLineLimits.Default

                                },
                                modifier = Modifier.fillMaxWidth()
                                           .onFocusChanged {

                                               isDescriptionFocused = it.isFocused

                                          },
                                decorator = { innerBox ->

                                   Column(modifier = Modifier.fillMaxWidth()) {

                                       //Si la caja de texto está vacía y NO tiene el foco
                                       if(state.description.text.toString().isEmpty() && !isDescriptionFocused){

                                           //Se muestra un Text de placeholder
                                           Text(text = stringResource(R.string.descrition),
                                                modifier = Modifier.fillMaxWidth(),
                                                color = MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.5f),
                                               style = MaterialTheme.typography.bodyLarge
                                           )
                                       }
                                       else{

                                           innerBox()

                                       }
                                   }
                               }
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    //enabled = state.canSaveTask,
                    onClick = {

                        onAction(TaskScreenAction.OnSaveTask)

                    },
                    modifier = Modifier.fillMaxWidth()
                               .padding(46.dp)) {

                    Text(text = stringResource(R.string.save),
                         style = MaterialTheme.typography.titleMedium.copy(
                             color = MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }
    }
}

//Preview para modo claro
@Composable
@Preview
fun TaskScreenLightPreview(
    @PreviewParameter(TaskScreenStatePreviewProvider::class) state:TaskScreenState){

    TodoAppTheme {

        TaskScreen(state = state, onAction = {})

    }
}

//Preview para modo oscuro
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TaskScreenDarkPreview(
    @PreviewParameter(TaskScreenStatePreviewProvider::class) state:TaskScreenState){

    TodoAppTheme {

        TaskScreen(state = state, onAction = {})

    }
}