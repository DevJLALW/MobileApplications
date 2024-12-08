package database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Users::class],
    version = 2
)

abstract class UsersDatabase: RoomDatabase() {

    abstract fun getUsersDao(): UsersDao
}