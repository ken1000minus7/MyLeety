package com.ken.myapplication.api

import com.ken.myapplication.data.User

sealed class LeetyApiResult(val data : User? = null){
    class Success(data: User?) : LeetyApiResult(data)
    class Failed : LeetyApiResult()
    class NonExistentUser : LeetyApiResult()
    class Loading : LeetyApiResult()
}
