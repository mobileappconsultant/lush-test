package com.example.falcon9launches.api

import com.example.falcon9launches.api.response.FalconResponse
import retrofit2.http.GET

interface FalconApiInterface {

    @GET("launches")
    suspend fun getFalconLaunches(): List<FalconResponse>
}