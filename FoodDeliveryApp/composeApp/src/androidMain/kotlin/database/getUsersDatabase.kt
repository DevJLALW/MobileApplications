package database

import android.content.Context
import android.util.Log
import androidx.room.Room



fun getUsersDatabase(context: Context?): UsersDatabase {
    requireNotNull(context) { "Context cannot be null" }
    val dbFile = context.getDatabasePath("users.db")
    Log.d("DatabasePath", "Database file path: ${dbFile.absolutePath}")
    return Room.databaseBuilder<UsersDatabase>(context = context.applicationContext,UsersDatabase::class.java,name=dbFile.absolutePath)
        .addMigrations(MIGRATION_1_2)
        .build()
}