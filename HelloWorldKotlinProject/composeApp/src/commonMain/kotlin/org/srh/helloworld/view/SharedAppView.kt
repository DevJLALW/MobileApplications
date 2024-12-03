package org.srh.helloworld.view

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.srh.helloworld.viewmodel.MainViewModel


@Composable
fun SharedAppView(viewModel: MainViewModel) {
    val context = LocalContext.current // Access the Android context
    val urlInput by viewModel.urlInput // Observe URL input state

    MaterialTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Aligns content to the left
            ){
            Text(
                text = viewModel.helloWorldText,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Red,
                    fontFamily = FontFamily.Serif
                )
            )}
            Spacer(modifier = Modifier.height(8.dp))
            SharedImage()
            Spacer(modifier = Modifier.height(16.dp))
            // TextField for entering a URL
            TextField(
                value = urlInput,
                onValueChange = { viewModel.onUrlInputChange(it) }, // Update ViewModel state
                label = { Text("Enter URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Button to open the entered URL
            Button(
                onClick = {
                    if (viewModel.isValidUrl(urlInput)) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlInput))
                        context.startActivity(intent) // Launch browser with the URL
                    } else {
                        // Show a Toast for invalid URL
                        Toast.makeText(context, "Invalid URL. Please enter a valid URL.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open URL")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSharedAppView() {
    val mockViewModel = MainViewModel().apply {
        onUrlInputChange("https://example.com")
    }
    SharedAppView(viewModel = mockViewModel)
}

