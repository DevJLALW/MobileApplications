package org.srh.fda.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.Alignment
import database.UsersViewModel


@Composable
fun LoginScreen(viewModel: UsersViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }

    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Button(onClick = {
                viewModel.authenticateUser(username, password) { success ->
                    loginMessage = if (success) "Login Successful" else "Invalid Credentials"
                }
            }) {
                Text("Login")
            }

            if (loginMessage.isNotEmpty()) {
                Text(text = loginMessage)
            }
        }
    }
}
