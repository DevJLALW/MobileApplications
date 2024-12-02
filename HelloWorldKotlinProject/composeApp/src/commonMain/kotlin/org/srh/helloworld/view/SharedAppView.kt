package org.srh.helloworld.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.srh.helloworld.controller.AppController

@Composable
fun SharedAppView(controller: AppController) {
    MaterialTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { controller.onButtonClick() }) {
                Text("Click me!")
            }

            AnimatedVisibility(controller.showContent.value) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SharedImage()
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = controller.inputText.value,
                        onValueChange = { controller.onTextInputChanged(it) },
                        label = { Text("Enter your text") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("You typed: ${controller.inputText.value}")
                }
            }
        }
    }
}
