package com.xalk.novabee.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xalk.novabee.api.ApiaryAPI
import com.xalk.novabee.models.ApiaryRequest
import com.xalk.novabee.models.ApiaryResponse
import com.xalk.novabee.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class ApiaryRepository @Inject constructor(private val apiaryAPI: ApiaryAPI) {

    private val _apiaryLiveData = MutableLiveData<NetworkResult<List<ApiaryResponse>>>()
    val apiaryLiveData: LiveData<NetworkResult<List<ApiaryResponse>>>
        get() = _apiaryLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLiveData

    suspend fun getApiaries() {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = apiaryAPI.getApiaries()
        if (response.isSuccessful && response.body() != null) {
            _apiaryLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _apiaryLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _apiaryLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun createApiary(apiaryRequest: ApiaryRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = apiaryAPI.createApiary(apiaryRequest)

        handleResponse(response, "Apiary Created")
    }



    suspend fun deleteApiary(apiaryId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = apiaryAPI.deleteApiary(apiaryId)
        handleResponse(response, "Apiary Deleted")

    }

    suspend fun updateApiary(apiaryId: String, apiaryRequest: ApiaryRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = apiaryAPI.updateApiary(apiaryId, apiaryRequest)
        handleResponse(response, "Apiary Updated")

    }


    private fun handleResponse(response: Response<ApiaryResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))

        } else {
            _statusLiveData.postValue(NetworkResult.Success("Something went wrong"))
        }
    }


}