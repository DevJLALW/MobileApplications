package org.srh.fda.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.srh.fda.R

@Composable
fun CheckoutScreen(navController: NavController) {
    MaterialTheme {
        // Set the background image and cover the entire screen
        Box(
            modifier = Modifier
                .fillMaxSize()
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
                    text = "Your Order has been placed",
                    color = Color.Black, // Text color to contrast the background
                    modifier = Modifier.padding(bottom = 20.dp),
                    style = MaterialTheme.typography.h5
                )

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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    // Provide a mock NavController using a rememberNavController in the Preview
    CheckoutScreen(navController = androidx.navigation.compose.rememberNavController())
}
