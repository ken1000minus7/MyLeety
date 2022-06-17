package com.ken.myapplication.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ken.myapplication.api.LeetyApiRepository
import com.ken.myapplication.api.LeetyApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val leetyApiRepository: LeetyApiRepository) : ViewModel() {
    val apiResult : MutableLiveData<LeetyApiResult> = MutableLiveData(null)

    fun getUser(username : String){
        viewModelScope.launch {
            apiResult.value = LeetyApiResult.Loading()
            apiResult.value = leetyApiRepository.getUser(username)
        }
    }

    fun getUser(){
        viewModelScope.launch {
            apiResult.value = LeetyApiResult.Loading()
            apiResult.value = leetyApiRepository.getUser()
        }
    }
}