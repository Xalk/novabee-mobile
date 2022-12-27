package com.xalk.novabee.api

import com.xalk.novabee.models.QueenRequest
import com.xalk.novabee.models.QueenResponse
import retrofit2.Response
import retrofit2.http.*

interface QueenAPI {

    @GET("api/apiary/{apiaryId}/beehive/{beehiveId}/queen")
    suspend fun getQueen(
        @Path("apiaryId") apiaryId: String,
        @Path("beehiveId") beehiveId: String
    ): Response<QueenResponse>

    @POST("api/apiary/{apiaryId}/beehive/{beehiveId}/queen")
    suspend fun createQueen(
        @Path("apiaryId") apiaryId: String,
        @Path("beehiveId") beehiveId: String,
        @Body queenRequest: QueenRequest
    ): Response<QueenResponse>

    @PATCH("api/apiary/{apiaryId}/beehive/{beehiveId}/queen")
    suspend fun updateQueen(
        @Path("apiaryId") apiaryId: String,
        @Path("beehiveId") beehiveId: String,
        @Body queenRequest: QueenRequest
    ): Response<QueenResponse>

}