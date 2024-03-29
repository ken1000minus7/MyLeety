package com.ken.myapplication.api

import com.ken.myapplication.data.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface LeetyApi {

    @GET("graphql")
    suspend fun getUser(
        @Query("query")
        body : String
    ) : Response<UserResponse>

}