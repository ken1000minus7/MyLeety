package com.ken.myapplication.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ken.myapplication.api.LeetyApiRepository
import com.ken.myapplication.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val leetyApiRepository: LeetyApiRepository) : ViewModel() {
    val user : MutableLiveData<User?> = MutableLiveData(null)

    fun getUser(username : String){
        viewModelScope.launch {
            user.value = leetyApiRepository.getUser(username)
            Log.d("lol",user.value?.username ?: "sadge")
        }
    }
}