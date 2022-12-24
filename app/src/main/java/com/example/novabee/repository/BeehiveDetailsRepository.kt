package com.example.novabee.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.novabee.api.BeehiveAPI
import com.example.novabee.models.BeehiveRequest
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class BeehiveDetailsRepository @Inject constructor(private val beehiveAPI: BeehiveAPI) {

    private val _oneBeehiveLiveData = MutableLiveData<NetworkResult<BeehiveResponse>>()
    val oneBeehiveLiveData: LiveData<NetworkResult<BeehiveResponse>>
        get() = _oneBeehiveLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLiveData


    suspend fun getBeehive(apiaryId: String, beehiveId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = beehiveAPI.getBeehive(apiaryId, beehiveId)

        if (response.isSuccessful && response.body() != null) {
            _oneBeehiveLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _oneBeehiveLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _oneBeehiveLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun createBeehive(apiaryId: String, beehiveRequest: BeehiveRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = beehiveAPI.createBeehive(apiaryId, beehiveRequest)
        handleResponse(response, "Beehive Created")
    }

    suspend fun updateBeehive(apiaryId: String, beehiveId: String, beehiveRequest: BeehiveRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = beehiveAPI.updateBeehive(apiaryId, beehiveId, beehiveRequest)
        handleResponse(response, "Beehive Updated")
    }

    suspend fun deleteBeehive(apiaryId: String, beehiveId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = beehiveAPI.deleteBeehive(apiaryId, beehiveId)
        handleResponse(response, "Beehive Deleted")
    }




    private fun handleResponse(response: Response<BeehiveResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Success("Something went wrong"))
        }
    }



}