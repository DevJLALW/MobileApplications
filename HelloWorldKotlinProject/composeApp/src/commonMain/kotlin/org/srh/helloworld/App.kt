package org.srh.helloworld


import androidx.compose.runtime.*

import org.jetbrains.compose.ui.tooling.preview.Preview

import org.srh.helloworld.controller.AppController
import org.srh.helloworld.view.SharedAppView

@Composable
@Preview
fun App() {

    // Initialize the controller for this screen
    val controller = remember { AppController() }

    // Pass the controller to the shared view
    SharedAppView(controller)
//    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
//    }
}