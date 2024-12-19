package org.srh.fda.viewmodel

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import database.Users
import database.UsersDao
import database.getUsersDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class UsersViewModel(private val context: Context?) : ViewModel() {

    private val usersDao: UsersDao = getUsersDatabase(context).getUsersDao()

    val _users = MutableStateFlow<List<Users>>(emptyList())
    val users: StateFlow<List<Users>> = _users

    private val _photoBitmap = MutableStateFlow<Bitmap?>(null)
    val photoBitmap: StateFlow<Bitmap?> = _photoBitmap

    private val _photoUri = MutableStateFlow<Uri?>(null)
    val photoUri: StateFlow<Uri?> = _photoUri

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    private val _loggedInUser = MutableStateFlow<Users?>(null)
    val loggedInUser: StateFlow<Users?> = _loggedInUser

    fun setLoggedInUser(user: Users?) {
        _loggedInUser.value = user
    }

    fun savePhoto(bitmap: Bitmap) {
        _photoBitmap.value = bitmap
    }

    init {
        fetchUsers()
    }

    fun savePhotoToStorage(bitmap: Bitmap): Uri? {
        val filename = "IMG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
        }

        val resolver = context?.contentResolver
        val imageUri = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            val outputStream: OutputStream? = resolver.openOutputStream(uri)
            outputStream?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
            _photoUri.value = uri
        }

        return imageUri
    }

    private fun fetchUsers() {
        viewModelScope.launch {

            usersDao.getAllUsers().collect {
                _users.value = it
            }
        }
    }

    open fun authenticateUser(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = usersDao.getUserByUsername(username)
            if (user?.password == password) {
                setLoggedInUser(user) // Set the logged-in user
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    fun registerUser(username: String, password: String, bitmap: Bitmap?, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val photoUri = bitmap?.let { savePhotoToStorage(it)?.toString() }
            val user = Users(username = username, password = password, photoUri = photoUri)
            usersDao.upsert(user)
            setLoggedInUser(user) // Set the logged-in user
            callback(true) // Indicate success
        }
    }
    fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}$"
        return Regex(passwordPattern).matches(password)
    }


    open fun addSampleUsers() {
        val sampleUsers = listOf(
            Users(username = "John", password = "Password1"),
            Users(username = "Jane", password = "Password2")
        )
        viewModelScope.launch {
            sampleUsers.forEach { user ->
                usersDao.upsert(user)
            }
        }
    }

    fun checkUsernameExists(username: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = usersDao.getUserByUsername(username)
            callback(user != null)
        }
    }
}