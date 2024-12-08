package org.srh.fda.navigate

import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import database.UsersViewModel
import org.srh.fda.view.LoginScreen
import org.srh.fda.view.MainScreen
import org.srh.fda.view.RegisterScreen

@Composable
fun AppNavigation(viewModel: UsersViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(viewModel = viewModel)
        }
        composable("register") {
            RegisterScreen(
                viewModel = viewModel,
                onRegisterComplete = { navController.popBackStack() }
            )
        }
    }
}
