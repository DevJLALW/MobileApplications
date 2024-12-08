package database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add the new column 'photoUri' to the 'users' table
        database.execSQL("ALTER TABLE users ADD COLUMN photoUri TEXT")
    }
}
