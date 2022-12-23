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

class BeehiveRepository @Inject constructor(private val beehiveAPI: BeehiveAPI) {

    private val _beehiveLiveData = MutableLiveData<NetworkResult<List<BeehiveResponse>>>()
    val beehiveLiveData: LiveData<NetworkResult<List<BeehiveResponse>>>
        get() = _beehiveLiveData

    private val _beehiveInfoLiveData = MutableLiveData<NetworkResult<BeehiveResponse>>()
    val beehiveInfoLiveData: LiveData<NetworkResult<BeehiveResponse>>
        get() = _beehiveInfoLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLiveData


    suspend fun getBeehives(apiaryId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = beehiveAPI.getBeehives(apiaryId)
        if (response.isSuccessful && response.body() != null) {
            _beehiveLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _beehiveLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _beehiveLiveData.postValue(NetworkResult.Error("Something went wrong"))
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


    suspend fun getBeehive(apiaryId: String, beehiveId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = beehiveAPI.getBeehive(apiaryId, beehiveId)

        if (response.isSuccessful && response.body() != null) {
            _beehiveInfoLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _beehiveInfoLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _beehiveInfoLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    private fun handleResponse(response: Response<BeehiveResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Success("Something went wrong"))
        }
    }


}