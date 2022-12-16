package com.example.novabee.api

import com.example.novabee.models.QueenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QueenAPI {

    @GET("api/apiary/{apiaryId}/beehive/{beehiveId}/queen")
    suspend fun getQueen(
        @Path("apiaryId") apiaryId: String,
        @Path("beehiveId") beehiveId: String
    ): Response<QueenResponse>

}