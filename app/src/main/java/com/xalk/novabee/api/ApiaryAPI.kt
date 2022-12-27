package com.xalk.novabee.api

import com.xalk.novabee.models.ApiaryRequest
import com.xalk.novabee.models.ApiaryResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiaryAPI {

    @GET("api/apiary")
    suspend fun getApiaries(): Response<List<ApiaryResponse>>

    @POST("api/apiary")
    suspend fun createApiary(@Body apiaryRequest: ApiaryRequest): Response<ApiaryResponse>

    @PATCH("api/apiary/{apiaryId}")
    suspend fun updateApiary(@Path("apiaryId") apiaryId: String, @Body apiaryRequest: ApiaryRequest): Response<ApiaryResponse>

    @DELETE("api/apiary/{apiaryId}")
    suspend fun deleteApiary(@Path("apiaryId") apiaryId: String): Response<ApiaryResponse>

}