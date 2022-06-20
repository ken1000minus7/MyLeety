package com.ken.myapplication.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [SavedUser::class],
    version = 2,
    exportSchema = false
)
abstract class SavedUserDatabase : RoomDatabase() {
    abstract fun savedUserDao() : SavedUserDao

}