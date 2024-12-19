package org.srh.fda.view

import android.annotation.SuppressLint
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.srh.fda.R
import org.srh.fda.viewmodel.UsersViewModel
import java.util.Locale

@SuppressLint("SuspiciousIndentation")
@Composable
fun CheckoutScreen(viewModel: UsersViewModel, navController: NavController) {

    val context = LocalContext.current
    val orderPlacedMessage = stringResource(id=R.string.order_status)

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope() // To launch coroutines for opening the drawer
    var tts: TextToSpeech? = null

     tts = remember {
        TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
                tts?.speak(orderPlacedMessage, TextToSpeech.QUEUE_FLUSH, null, null)
            } else {
                println("TTS initialization failed")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tts.shutdown()
        }
    }

    // Retrieve the location from the saved state handle
    val selectedLocation = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>("selectedLocation")




        // Set the background image and cover the entire screen

        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                ProfileScreen(viewModel = viewModel, navController = navController)
            },
            topBar = {
                TopAppBar(
                    title = { Text(text = "Checkout") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { // Open the drawer within a coroutine
                                    scaffoldState.drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Set the background image (make sure you have an image in the drawable folder)
                Image(
                    painter = painterResource(R.drawable.backgroundimg5), // Replace with your background image name
                    contentDescription = "Background image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {

                    Image(
                        painter = painterResource(id = R.drawable.image1),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Fit
                    )

                    // Text to confirm the order has been placed
                    Text(
                        text = orderPlacedMessage,
                        color = Color.Black, // Text color to contrast the background
                        modifier = Modifier.padding(bottom = 20.dp),
                        style = MaterialTheme.typography.h5
                    )


                    selectedLocation?.let {
                        Text(
                            text = "Selected Location: $it",
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                    // Button to navigate back to the home screen
                    Button(
                        onClick = {
                            // Navigate back to the main screen
                            navController.navigate("main") {
                                // Pop the checkout screen from the back stack
                                popUpTo("checkout") { inclusive = true }
                            }
                        },
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Text("Back to Home", color = Color.White) // Text color for the button
                    }

                    Button(
                        onClick = {
                            // Navigate back to the main screen
                            navController.navigate("maps")
                        },
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Text("Check Current Location", color = Color.White) // Text color for the button
                    }


                }
            }
        }

}

//@Preview(showBackground = true)
//@Composable
//fun CheckoutScreenPreview() {
//    // Provide a mock NavController using a rememberNavController in the Preview
//    CheckoutScreen(navController = androidx.navigation.compose.rememberNavController())
//}
