package com.developer.todoapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.developer.todoapp.presentation.screens.detail.TaskScreenRoot
import com.developer.todoapp.presentation.screens.home.HomeScreenRoot

@Composable
fun NavigationRoot(navHostController:NavHostController){

    Box(modifier = Modifier.fillMaxSize()){

        NavHost(navController = navHostController,
                startDestination = HomeScreenRoute){

            composable<HomeScreenRoute>{

                HomeScreenRoot(navigateToTaskScreen = {

                    //Se usa para navegar de HomeScreenRoot a TaskScreenRoot
                    navHostController.navigate(route = TaskScreenRoute(id = it))
                    //navHostController.navigate(TaskScreenRoute)
                })
            }

            composable <TaskScreenRoute> {

                TaskScreenRoot(backToHomeScreen = {

                    /**
                     * Se usa para navegar de TaskScreenRoot a HomeScreenRoot
                     * (Ir a la pantalla anterior)
                    */
                    //navHostController.popBackStack()
                    navHostController.navigateUp()

                })
            }
        }
    }

}