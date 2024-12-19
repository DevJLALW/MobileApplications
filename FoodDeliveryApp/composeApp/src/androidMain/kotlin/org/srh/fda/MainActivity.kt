package org.srh.fda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import database.Users
import org.srh.fda.viewmodel.UsersViewModel
import org.srh.fda.navigate.AppNavigation
import org.srh.fda.theme.AppTheme

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf

import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import org.srh.fda.model.OrderState
import org.srh.fda.view.ProductDetailsScreen

private const val PRODUCT_PRICE_PER_UNIT = 5.25
private const val PRODUCT_CURRENCY = "€"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = UsersViewModel(this)
        setContent {
           // AppNavigation(viewModel = viewModel)
            AppTheme {
                AppNavigation(viewModel = viewModel)

            }

        }
    }
}

@Composable
fun OrderPage(navController: NavController, viewModel: UsersViewModel) {
    var amount by remember { mutableIntStateOf(0) } // Start with default amount
    val totalPrice by remember { derivedStateOf { amount * PRODUCT_PRICE_PER_UNIT } }

    // Pass the navController to ProductDetailsScreen
    ProductDetailsScreen(
        viewModel = viewModel,
        navController = navController, // Passing navController here
        orderState = OrderState(
            amount = amount,
            totalPrice = "$PRODUCT_CURRENCY${"%.2f".format(totalPrice)}"
        ),
        onAddItemClicked = {
            amount += 1 // Increment amount
        },
        onRemoveItemClicked = {
            if (amount > 0) amount -= 1 // Decrement amount only if greater than 0
        },
        onCheckOutClicked = {
            navController.navigate("checkout") // Navigate to checkout screen
        }
    )
}





@Preview
@Composable
fun AppAndroidPreview() {
    // Provide a mock ViewModel with test data
    val mockViewModel = object : UsersViewModel(context = null) { // Passing `null` as context for mock
        init {
            // Mock sample users
            val sampleUsers = listOf(
                Users(username = "John", password = "Password1"),
                Users(username = "Jane", password = "Password2")
            )
            _users.value = sampleUsers // Directly populate the StateFlow
        }

        override fun addSampleUsers() {
            // No-op for mock
        }
    }

    // Pass the mock ViewModel to the App function
    app(viewModel = mockViewModel)
}