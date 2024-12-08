package org.srh.fda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import database.Users
import database.UsersViewModel
import org.srh.fda.navigate.AppNavigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = UsersViewModel(this)
        setContent {
            AppNavigation(viewModel = viewModel)

        }
    }
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