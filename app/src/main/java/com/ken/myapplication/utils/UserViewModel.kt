package com.ken.myapplication.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var user : User? by mutableStateOf(null)

    fun getUser(username : String){
        viewModelScope.launch {
            user = leetyApiRepository.getUser(username)
        }
    }
}