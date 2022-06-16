package com.ken.myapplication.room

import androidx.lifecycle.LiveData

interface SavedUserRepository {
    fun getAllUsers() : LiveData<List<SavedUser>>
    suspend fun insertUser(user : SavedUser)
    suspend fun deleteUser(user : SavedUser)
    suspend fun getUser(username : String) : SavedUser?
}