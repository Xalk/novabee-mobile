package com.example.novabee.api

import com.example.novabee.models.BeehiveRequest
import com.example.novabee.models.BeehiveResponse
import retrofit2.Response
import retrofit2.http.*

interface BeehiveAPI {

    @POST("api/apiary/{apiaryId}/beehive")
    suspend fun createBeehive(
        @Path("apiaryId") apiaryId: String,
        @Body beehiveRequest: BeehiveRequest
    ): Response<BeehiveResponse>

    @GET("api/apiary/{apiaryId}/beehive")
    suspend fun getBeehives(@Path("apiaryId") apiaryId: String): Response<List<BeehiveResponse>>

    @GET("api/apiary/{apiaryId}/beehive/{beehiveId}")
    suspend fun getBeehive(
        @Path("apiaryId") apiaryId: String,
        @Path("beehiveId") beehiveId: String
    ): Response<BeehiveResponse>

    @DELETE("api/apiary/{apiaryId}/beehive/{beehiveId}")
    suspend fun deleteBeehive(
        @Path("apiaryId") apiaryId: String,
        @Path("beehiveId") beehiveId: String
    ): Response<BeehiveResponse>

    @PATCH("api/apiary/{apiaryId}/beehive/{beehiveId}")
    suspend fun updateBeehive(
        @Path("apiaryId") apiaryId: String,
        @Path("beehiveId") beehiveId: String,
        @Body beehiveRequest: BeehiveRequest
    ): Response<BeehiveResponse>


}