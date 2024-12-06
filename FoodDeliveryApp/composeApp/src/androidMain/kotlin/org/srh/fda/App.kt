package org.srh.fda


import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview



import database.UsersViewModel


@Composable
fun app(viewModel: UsersViewModel) {
    MaterialTheme {


        val users = viewModel.users.collectAsState(emptyList()).value

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Displaying users from the database
            users.forEach { user ->
                Text(text = "User: ${user.username}")
            }

            // Button to add sample users
            Button(onClick = {
                viewModel.addSampleUsers()
            }) {
                Text("Add Sample Users")
            }
        }
    }
}

@Preview
@Composable
fun appPreview(){
    app(viewModel = UsersViewModel(
        context = TODO()
    ))
}