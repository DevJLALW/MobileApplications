package org.srh.fda.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(navController: NavController) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { navController.navigate("login") }
            ) {
                Text("Login")
            }

            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { navController.navigate("register") }
            ) {
                Text("Register")
            }
        }
    }
}

@Preview
@Composable
fun mainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}
