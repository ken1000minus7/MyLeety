package com.ken.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SavedUser::class],
    version = 1,
    exportSchema = false
)
abstract class SavedUserDatabase : RoomDatabase() {
    abstract fun savedUserDao() : SavedUserDao
}