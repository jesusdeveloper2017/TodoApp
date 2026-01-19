package com.developer.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.developer.todoapp.navigation.NavigationRoot
import com.developer.todoapp.ui.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Se usa la anotación @AndroidEntryPoint
 * para indicar que este es el punto de entrada
 * de la aplicación donde se va a inyectar toda
 * la dependencia necesaria de Hilt
*/
@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState:Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            TodoAppTheme {

                val navHostController:NavHostController = rememberNavController()

                NavigationRoot(navHostController = navHostController)

            }
        }
    }
}