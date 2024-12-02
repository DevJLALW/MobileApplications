package org.srh.helloworld.controller

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.srh.helloworld.model.UserInputModel

class AppController {
    val userInputModel = UserInputModel()
    val showContent: MutableState<Boolean> = mutableStateOf(false)
    val inputText: MutableState<String> = mutableStateOf(userInputModel.inputText)

    fun onButtonClick() {
        showContent.value = !showContent.value
    }

    fun onTextInputChanged(newText: String) {
        inputText.value = newText
        userInputModel.inputText = newText
    }


}