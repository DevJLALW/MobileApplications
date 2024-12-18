package org.srh.fda.navigate

import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import database.UsersViewModel
import org.srh.fda.OrderPage
import org.srh.fda.view.*

@Composable
fun AppNavigation(viewModel: UsersViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            IntroScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(viewModel = viewModel, onLoginComplete = {
                navController.navigate("profile")
            })
        }
        composable("register") {
            RegisterScreen(viewModel = viewModel, onRegisterComplete = {
                navController.navigate("profile")
            })
        }
        composable("profile") {
            ProfileScreen(viewModel = viewModel, navController = navController)
        }

        composable("products") {
            ProductDetailsScreen(
                onAddItemClicked = {
                    navController.navigate("order_page")
                },
                onRemoveItemClicked = {
                    navController.navigate("order_page")
                },
                onCheckOutClicked = {
                    navController.navigate("checkout")
                },
                navController = navController // Ensure navController is passed here
            )
        }

//        composable("order_page") {
//            OrderPage(
//                navController = navController,
//                viewModel = viewModel,  // Pass the UsersViewModel here
//                onOrderComplete = {
//                    navController.navigate("checkout") // Navigate to checkout screen
//                }
//            )
//        }

//        composable("order_page") {
//            OrderPage(navController = navController) // Order page with navigation to checkout
//        }
        composable("order_page") {
            // Pass the missing parameters here
            OrderPage(
                navController = navController, // Pass navController here
                viewModel = viewModel // Pass viewModel here
            )
        }

        composable("checkout") {
            CheckoutScreen(navController = navController) // Checkout screen
        }
        composable("maps") {
            MapScreenDroid(navController = navController)
        }
    }
}