package com.xalk.novabee.api

import com.xalk.novabee.models.UserRequest
import com.xalk.novabee.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI{

    @POST("/api/auth/register")
    suspend fun register(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body userRequest: UserRequest) : Response<UserResponse>
}