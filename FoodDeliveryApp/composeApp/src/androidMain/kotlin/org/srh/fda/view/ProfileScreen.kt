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
import androidx.compose.ui.res.painterResource
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
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))
                //Text(text = "Image Path: ${imagePath ?: "Unknown"}")
            } ?: Text("No profile picture available")
        } ?: Text("No user data available")

        Spacer(modifier = Modifier.height(24.dp))

//        Button(onClick = { navController.navigate("products") }) {
//            Text("Go to Main Screen")
//        }

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
