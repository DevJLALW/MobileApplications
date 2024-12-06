package database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true) val userid: Int =0,
    val username: String,
    val password: String
)
