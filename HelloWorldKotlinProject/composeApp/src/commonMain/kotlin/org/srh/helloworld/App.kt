package org.srh.helloworld


import androidx.compose.runtime.*

import org.jetbrains.compose.ui.tooling.preview.Preview


import org.srh.helloworld.view.SharedAppView
import org.srh.helloworld.viewmodel.MainViewModel

@Composable
@Preview
fun App() {


    // Pass the controller to the shared view
    SharedAppView(viewModel = MainViewModel())

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