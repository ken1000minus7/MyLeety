package com.ken.myapplication.room

import androidx.lifecycle.LiveData
import javax.inject.Inject

class SavedUserRepositoryImpli @Inject constructor(private val savedUserDatabase: SavedUserDatabase) : SavedUserRepository {
    private val savedUserDao = savedUserDatabase.savedUserDao()
    override fun getAllUsers(): LiveData<List<SavedUser>> {
        return savedUserDao.getAllUsers()
    }

    override suspend fun insertUser(user: SavedUser) {
        savedUserDao.insertUser(user)
    }

    override suspend fun deleteUser(user: SavedUser) {
        savedUserDao.deleteUser(user)
    }

    override suspend fun getUser(username: String): SavedUser? {
        return savedUserDao.getUser(username)
    }
}