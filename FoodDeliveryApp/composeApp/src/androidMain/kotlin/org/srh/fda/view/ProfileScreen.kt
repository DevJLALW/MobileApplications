package org.srh.fda.view

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import org.srh.fda.viewmodel.UsersViewModel
import org.srh.fda.R

@Composable
fun ProfileScreen(viewModel: UsersViewModel, navController: NavController) {
    val user by viewModel.users.collectAsState()
    val context = LocalContext.current
    //val latestUser = user.lastOrNull() // Fetch the most recently added user
    val loggedInUser by viewModel.loggedInUser.collectAsState()

    var imagePath by remember { mutableStateOf<String?>(null) }

    var showPasswordFields by remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordMessage by remember { mutableStateOf("") }

    LaunchedEffect(loggedInUser?.photoUri) {
        loggedInUser?.photoUri?.let { uriString ->
            val uri = Uri.parse(uriString)
            imagePath = getFileMetadata(context, uri)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.backgroundimg3),
            contentDescription = "Background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
            loggedInUser?.let {
            Text(text = "Username: ${it.username}")
            Spacer(modifier = Modifier.height(16.dp))

            it.photoUri?.let { uriString ->
                Image(
                    painter = rememberAsyncImagePainter(Uri.parse(uriString)),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                    .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))
                //Text(text = "Image Path: ${imagePath ?: "Unknown"}")
            } ?: Text("No profile picture available")
        } ?: Text("No user data available")

        Spacer(modifier = Modifier.height(24.dp))

            // Button to show password update fields
            Button(onClick = { showPasswordFields = !showPasswordFields }) {
                Text("Update Password")
            }

            // Show password update fields if toggled
            if (showPasswordFields) {
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if (newPassword != confirmPassword) {
                        passwordMessage = "Passwords do not match."
                    } else {
                        // Call the function to update the password
                        loggedInUser?.let { user ->
                            viewModel.updatePassword(user.username, newPassword) { success ->
                                if (success) {
                                    passwordMessage = "Password updated successfully."
                                } else {
                                    passwordMessage = "Failed to update password."
                                }
                            }
                        }
                    }
                }) {
                    Text("Update Password")
                }

                if (passwordMessage.isNotEmpty()) {
                    Text(text = passwordMessage)
                }
            }

            Button(onClick = { navController.navigate("main") }) {
                Text("Logout")
            }
    }}
}

// Function to get file metadata (image path)
fun getFileMetadata(context: Context, uri: Uri): String? {

    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val dataIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val fullPath = it.getString(dataIndex)

            // Log the full path
            Log.d("ImagePath", "Full image path: $fullPath")

            return fullPath
        }
    }
    return null
}
