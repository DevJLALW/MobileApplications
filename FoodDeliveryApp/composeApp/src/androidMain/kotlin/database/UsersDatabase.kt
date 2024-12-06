package database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Users::class],
    version = 1
)

abstract class UsersDatabase: RoomDatabase() {

    abstract fun getUsersDao(): UsersDao
}