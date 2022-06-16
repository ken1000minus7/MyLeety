package com.ken.myapplication.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SavedUserDao {
    @Query("SELECT * FROM SavedUser")
    fun getAllUsers() : LiveData<List<SavedUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : SavedUser)

    @Delete
    suspend fun deleteUser(user: SavedUser)

    @Query("SELECT * FROM SavedUser WHERE username = :username")
    suspend fun getUser(username : String) : SavedUser?
}