package com.developer.todoapp.presentation.screens.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developer.todoapp.ui.theme.TodoAppTheme

@Composable
fun SummaryInfo(modifier:Modifier = Modifier,
                date:String = "March 9, 2024",
                taskSummary:String = "5 incompleted, 5 completed",
                completedTasks:Int = 30,
                totalTasks:Int = 50){


    val angleRatio:Animatable<Float, AnimationVector1D> = remember {
        Animatable(initialValue = 0f)
    }

    //Se ejecuta cada vez que completedTasks o totalTasks cambia
    LaunchedEffect(key1 = completedTasks, key2 = totalTasks) {

        //Si no hay tareas
        if(totalTasks == 0){
            //NO se hace nada
            return@LaunchedEffect
        }

        /**
         * Se calcula el porcentaje de tareas completadas y se hace una
         * animación de ese porcentaje
        */
        angleRatio.animateTo(targetValue = completedTasks.toFloat()/totalTasks.toFloat(),
                             animationSpec = tween(durationMillis = 300)
        )
    }

    Row {

        Column(modifier = Modifier.padding(16.dp)
                         .weight(1.5f)) {

            Text(
                text = date,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = taskSummary,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.padding(16.dp)
                       .weight(1f)) {

            val colorBase = MaterialTheme.colorScheme.inversePrimary
            val colorProgress = MaterialTheme.colorScheme.primary
            val strokeWidth = 16.dp

            Canvas(modifier = Modifier.aspectRatio(1f)) {

                //Se diubuja el circulo inferior
                drawArc(color = colorBase,
                        startAngle = 0f, /*Para que empiece desde el centro derecho del círculo*/
                        sweepAngle = 360f /*Para que termine el círculo completo*/,
                        useCenter = false,
                        size = size,
                        style = Stroke(width = strokeWidth.toPx(),
                                       cap = StrokeCap.Round)

                )

                /**
                 * Si la cantidad de tareas completadas
                 * es menor o igual a la cantidad total de tareas
                */
                if(completedTasks <= totalTasks){

                    //Se dibuja el circulo superior
                    drawArc(color = colorProgress,
                            startAngle = 90f, /*Para que empiece desde el centro inferior del círculo*/
                            sweepAngle = 360f * angleRatio.value, /*Para que termine el círculo hasta donde se haya completado*/
                            useCenter = false,
                            size = size,
                            style = Stroke(width = strokeWidth.toPx(), 
                                           cap = StrokeCap.Round)
                    )
                }
            }

            Text(text = "${(completedTasks/totalTasks.toFloat()).times(100).toInt()}%",
                 style = MaterialTheme.typography.headlineMedium,
                 color = MaterialTheme.colorScheme.onSurface,
                 fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryInfoPreview(){

    TodoAppTheme{
        SummaryInfo()
    }
}
