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

                /*
                var isShown:Boolean by remember { mutableStateOf(false) }

                Column(modifier = Modifier.padding(56.dp)) {

                    if (isShown) {
                        Text(text = "This is a message")
                    }
                    Text(text = "Hello, world!",
                         modifier = Modifier.clickable {
                                        isShown = !isShown
                                    }
                    )
                }
                */

                HelloWorldView()

            }
        }
    }
}

@Composable
fun HelloWorldView() {

    var isShown:Boolean by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(56.dp)) {

        MessageText(isShown)

        HelloWorldText(onClick = {
            //Se le asigna el valor contrario
            isShown = !isShown
        })

    }
}

@Composable
fun MessageText(isShown:Boolean) {

    if (isShown) {
        Text(text = "This is a message")
    }

}

@Composable
fun HelloWorldText(onClick: () -> Unit) {

    Text(text = "Hello, world!",
         modifier = Modifier.clickable {
                        onClick()
                    }
    )
}


