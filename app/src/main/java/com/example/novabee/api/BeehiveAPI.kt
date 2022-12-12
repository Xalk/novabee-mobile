package com.example.novabee.api

import com.example.novabee.models.BeehiveResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BeehiveAPI {

    @GET("api/apiary/{apiaryId}/beehive")
    suspend fun getBeehives(@Path("apiaryId") apiaryId: String): Response<List<BeehiveResponse>>

}