package org.srh.fda.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LanguageViewModel : ViewModel() {

    private val _selectedLanguage = MutableStateFlow("en") // Default to English
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    fun setLanguage(language: String) {
        _selectedLanguage.value = language
    }
}