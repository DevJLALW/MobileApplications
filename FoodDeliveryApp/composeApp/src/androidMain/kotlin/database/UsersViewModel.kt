package database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class UsersViewModel(context: Context?) : ViewModel() {

    private val usersDao: UsersDao = getUsersDatabase(context).getUsersDao()

    val _users = MutableStateFlow<List<Users>>(emptyList())
    val users: StateFlow<List<Users>> = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {

            usersDao.getAllUsers().collect {
                _users.value = it
            }
        }
    }

    fun authenticateUser(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = usersDao.getUserByUsername(username)
            callback(user?.password == password)
        }
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
}
