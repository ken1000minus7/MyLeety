package com.ken.myapplication.api

import com.ken.myapplication.data.User

interface LeetyApiRepository {

    suspend fun getUser() : LeetyApiResult
    suspend fun getUser(username : String) : LeetyApiResult

}