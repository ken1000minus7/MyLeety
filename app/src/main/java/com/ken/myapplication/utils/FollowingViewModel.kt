package com.ken.myapplication.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ken.myapplication.room.SavedUser
import com.ken.myapplication.room.SavedUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(private val savedUserRepository: SavedUserRepository): ViewModel() {
    var userList = savedUserRepository.getAllUsers()
    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO){
            userList = savedUserRepository.getAllUsers()
        }
    }

    fun insertUser(user : SavedUser){
        viewModelScope.launch(Dispatchers.IO) {
            savedUserRepository.insertUser(user)
        }
    }

    fun deleteUser(user: SavedUser){
        viewModelScope.launch(Dispatchers.IO) {
            savedUserRepository.deleteUser(user)
        }
    }
}