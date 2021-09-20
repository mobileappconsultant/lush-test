package com.example.falcon9launches.data

import com.example.falcon9launches.api.FalconApiInterface
import com.example.falcon9launches.api.response.FalconResponse
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Inject

class FalconLaunchDataStore @Inject constructor(private val retrofit: Retrofit) {

    suspend fun getFalconLaunches() : ResultWrapper<List<FalconResponse>>{
        val falconServices = retrofit.create(FalconApiInterface::class.java)
        return  safeApiCall(Dispatchers.IO){
            falconServices.getFalconLaunches()
        }
    }
}