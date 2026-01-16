package com.developer.todoapp.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developer.todoapp.domain.Category
import com.developer.todoapp.domain.Task
import com.developer.todoapp.ui.theme.TodoAppTheme
import java.util.UUID

@Composable
fun TaskItem(modifier:Modifier = Modifier,
             task:Task,
             onClickItem: (String)-> Unit,
             onDeleteItem: (String)-> Unit,
             onToggleCompletion: (Task)-> Unit){

    Row(modifier = modifier.clickable{
                       onClickItem(task.id)
                   }
                   .background(color = MaterialTheme.colorScheme.surfaceContainer)
                   .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Checkbox(checked = task.isCompleted,
                 onCheckedChange = {

                     onToggleCompletion(task)

                 }
        )

        Column(modifier = Modifier.padding(8.dp).weight(1f),
               horizontalAlignment = Alignment.Start,
               verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(text = task.title,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis,
                 fontWeight = FontWeight.Bold,
                 style = MaterialTheme.typography.titleSmall.copy(
                     //Se coloca una línea sobre el texto dependiendo del Boolean
                     textDecoration = if(task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                 ),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            if(!task.isCompleted){

                task.description?.let { //if (task.description != null)

                    Text(text = it,
                         maxLines = 2,
                         overflow = TextOverflow.Ellipsis,
                         style = MaterialTheme.typography.bodySmall,
                         color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }

                task.category?.let { //if (task.category != null)

                    Text(text = it.toString(),
                         style = MaterialTheme.typography.bodySmall,
                         color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }
            }
        }

        Box{

            Icon(imageVector = Icons.Default.Delete,
                 contentDescription = "Delete",
                 tint = MaterialTheme.colorScheme.onSurfaceVariant,
                 modifier = Modifier.padding(8.dp)
                            .clickable{

                                onDeleteItem(task.id)

                            }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview(){

    TodoAppTheme{

        TaskItem(onClickItem = {},
                 onDeleteItem = {},
                 onToggleCompletion = {},
                 task = Task(id = UUID.randomUUID().toString(),
                             title = "Tarea 1",
                             description = "Descripción",
                             isCompleted = false,
                             category = Category.WORK)
        )
    }
}