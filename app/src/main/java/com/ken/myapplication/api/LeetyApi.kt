package com.ken.myapplication.api

import com.ken.myapplication.data.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LeetyApi {

    @GET("graphql")
    suspend fun getUser(
        @Query("query")
        query : String
    ) : Response<UserResponse>

}