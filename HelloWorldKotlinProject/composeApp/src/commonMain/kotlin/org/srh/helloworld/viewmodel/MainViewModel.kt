package org.srh.helloworld.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.srh.helloworld.model.TextModel

class MainViewModel: ViewModel() {
    val helloWorldText = TextModel().helloWorldText
    // State to hold the entered URL
    val urlInput = mutableStateOf("")

    // Utility function to validate URLs
    fun isValidUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://")
    }

    // Update URL input state
    fun onUrlInputChange(newUrl: String) {
        urlInput.value = newUrl
    }
}