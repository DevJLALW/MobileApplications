package org.srh.fda.view

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import database.UsersViewModel

@Composable
fun ProfileScreen(viewModel: UsersViewModel, navController: NavController) {
    val user by viewModel.users.collectAsState()
    val context = LocalContext.current
    val latestUser = user.lastOrNull() // Fetch the most recently added user

    var imagePath by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(latestUser?.photoUri) {
        latestUser?.photoUri?.let { uriString ->
            val uri = Uri.parse(uriString)
            imagePath = getFileMetadata(context, uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        latestUser?.let {
            Text(text = "Username: ${it.username}")
            Spacer(modifier = Modifier.height(16.dp))

            it.photoUri?.let { uriString ->
                Image(
                    painter = rememberAsyncImagePainter(Uri.parse(uriString)),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Image Path: ${imagePath ?: "Unknown"}")
            } ?: Text("No profile picture available")
        } ?: Text("No user data available")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate("main") }) {
            Text("Go to Main Screen")
        }
    }
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
