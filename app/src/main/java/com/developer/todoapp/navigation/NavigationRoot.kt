package com.developer.todoapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.developer.todoapp.presentation.screens.detail.TaskScreenRoot
import com.developer.todoapp.presentation.screens.detail.TaskScreenViewModel
import com.developer.todoapp.presentation.screens.home.HomeScreenRoot
import com.developer.todoapp.presentation.screens.home.HomeScreenViewModel

@Composable
fun NavigationRoot(navHostController:NavHostController){

    Box(modifier = Modifier.fillMaxSize()){

        NavHost(navController = navHostController,
                startDestination = HomeScreenRoute){

            composable<HomeScreenRoute>{

                /**
                 * Se crea el viewmodel con hiltViewModel<> y
                 * este ya contiene los parámetros que necesita
                 * para funcionar
                */
                val viewModel = hiltViewModel<HomeScreenViewModel>()

                HomeScreenRoot(navigateToTaskScreen = {

                    //Se usa para navegar de HomeScreenRoot a TaskScreenRoot
                    navHostController.navigate(route = TaskScreenRoute(id = it))
                    //navHostController.navigate(TaskScreenRoute)
                },
                viewModel = viewModel)
            }

            composable <TaskScreenRoute> {

                /**
                 * Se crea el viewmodel con hiltViewModel<> y
                 * este ya contiene los parámetros que necesita
                 * para funcionar
                */
                val viewModel = hiltViewModel<TaskScreenViewModel>()

                TaskScreenRoot(backToHomeScreen = {

                    /**
                     * Se usa para navegar de TaskScreenRoot a HomeScreenRoot
                     * (Ir a la pantalla anterior)
                    */
                    //navHostController.popBackStack()
                    navHostController.navigateUp()

                },
                viewModel = viewModel)
            }
        }
    }
}