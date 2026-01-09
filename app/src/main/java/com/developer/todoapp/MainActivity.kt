package com.developer.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developer.todoapp.ui.theme.TodoAppTheme

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            TodoAppTheme {

                /**
                 * Código donde todas las vistas o composables tienen acceso
                 * a la variable isShown lo que hace que cada vez que esta
                 * variable se actualice todas las vistas se actualicen o pasen
                 * por el fenómeno de recomposisión aunque no dependan de esta
                 * variable
                */
                /*
                var isShown:Boolean by remember { mutableStateOf(false) }

                Column(modifier = Modifier.padding(56.dp)) {

                    if (isShown) {
                        Text(text = "Dependo de isShown")
                    }

                    Text(text = "NO Dependo de isShown (clickeame)",
                         modifier = Modifier.clickable {
                                        isShown = !isShown
                                    }
                    )
                }
                */

                /**
                 * Optimización de la Reposición en Interfaces con Jetpack Compose:
                 *
                 * Con este enfoque la variable isShown
                 * solo es accedida dentro de la función MainView()
                 * y dentro de la función Text1() porque la recibe por parámetro para
                 * leerla dentro de Text1().
                 *
                 * Como isShown NO se usa directamente dentro de la función Text2() sino
                 * que se modifica dentro del contexto de la función MainView(),
                 * las vistas que se encuentran dentro de la función Text2()
                 * NO se recomponen cuando la variable isShown cambia su valor
                */
                MainView()

            }
        }
    }
}

@Composable
fun MainView() {

    var isShown:Boolean by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(56.dp)) {

        Text1(isShown)

        Text2(onClick = {

            //Se le asigna el valor contrario
            isShown = !isShown
        })

    }
}

@Composable
fun Text1(isShown:Boolean) {

    if (isShown) {
        Text(text = "Dependo de isShown")
    }

}

@Composable
fun Text2(onClick: () -> Unit) {

    Text(text = "Dependo de isShown (clickeame)",
         modifier = Modifier.clickable {
                        onClick()
                    }
    )
}


