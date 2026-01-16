package com.developer.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.developer.todoapp.navigation.NavigationRoot
import com.developer.todoapp.ui.theme.TodoAppTheme

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