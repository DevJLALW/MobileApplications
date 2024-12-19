package org.srh.fda.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.srh.fda.viewmodel.UsersViewModel
import org.srh.fda.R


@Composable
fun LoginScreen(viewModel: UsersViewModel, onLoginComplete: (String) -> Unit) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }

    MaterialTheme {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.backgroundimg4),
                contentDescription = "Background image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column( modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(200.dp))

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
                    if (success) {
                        loginMessage = "Login Successful"
                        onLoginComplete(username)  // Navigate to ProfileScreen
                    } else {
                        loginMessage = "Invalid Credentials"
                    }
                }
            }) {
                Text("Login")
            }

            if (loginMessage.isNotEmpty()) {
                Text(text = loginMessage)
            }


        }
    }}
}

@Preview
@Composable
fun loginScreenPreview()
{
    val fakeViewModel = object : UsersViewModel(null) {
        override fun authenticateUser(username: String, password: String, callback: (Boolean) -> Unit) {
            callback(username == "John" && password == "Password1")
        }

        override fun addSampleUsers() {
            // No-op for the fake implementation
        }
    }
    //LoginScreen(viewModel = fakeViewModel)
}
