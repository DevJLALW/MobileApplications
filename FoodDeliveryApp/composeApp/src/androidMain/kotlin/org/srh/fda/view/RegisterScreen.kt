package org.srh.fda.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil3.compose.rememberAsyncImagePainter
import org.srh.fda.viewmodel.UsersViewModel
import org.srh.fda.R
import org.srh.fda.viewmodel.LanguageViewModel


@Composable
fun RegisterScreen(viewModel: UsersViewModel, languageViewModel: LanguageViewModel, onRegisterComplete: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var photoUri by remember {mutableStateOf<Uri?>(null)}
    var confirmPassword by remember { mutableStateOf("") }
    val currentLanguage = languageViewModel.selectedLanguage.collectAsState().value

    val context = LocalContext.current
    val photoBitmap by viewModel.photoBitmap.collectAsState()


    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            viewModel.savePhoto(it)

            photoUri = null
        }
    }
    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        }
    }



    MaterialTheme {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.backgroundimg2),
                contentDescription = "Background image",
                modifier = Modifier.fillMaxSize(),
               contentScale = ContentScale.Crop
            )



            Column ( modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(200.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(id = R.string.username)) }

            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),

            )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text(stringResource(id = R.string.confirm_password)) },
                    visualTransformation = PasswordVisualTransformation()
                )

            Spacer(modifier = Modifier.height(16.dp))


            Button(onClick = {
                when {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                        cameraLauncher.launch(null)
                    }
                    else -> {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            }) {
                Text(stringResource(id=R.string.capture_photo))
            }


            Spacer(modifier = Modifier.height(16.dp))

            photoUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Captured Photo",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }


                Button(onClick = {
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        if (password != confirmPassword) {
                            Toast.makeText(context, "Password do not match", Toast.LENGTH_SHORT).show()
                        } else {viewModel.checkUsernameExists(username) { exists ->
                            if (exists) {
                                Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show()
                            } else {

                                if (!viewModel.isPasswordValid(password)) {
                                    message = "Password must be at least 8 characters long, with uppercase, lowercase, digit, and special character"
                                }
                                else {
                                    viewModel.registerUser(username, password, photoBitmap) { success ->
                                        if (success) {
                                            message = "User Registered Successfully"
                                            onRegisterComplete(username)
                                        } else {
                                            message = "Registration Failed"
                                        }
                                    }
                                }
                            }
                        }}
                    } else {
                        message = "Please fill all fields"
                    }
                }) {
                    Text(text = stringResource(id = R.string.register))
                }

                if (message.isNotEmpty()) {
                    Text(text = message)
                }
            }
        }
    }
}