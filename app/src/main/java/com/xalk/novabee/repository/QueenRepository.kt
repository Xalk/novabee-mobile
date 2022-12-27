package com.xalk.novabee.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xalk.novabee.api.QueenAPI
import com.xalk.novabee.models.BeehiveRequest
import com.xalk.novabee.models.BeehiveResponse
import com.xalk.novabee.models.QueenRequest
import com.xalk.novabee.models.QueenResponse
import com.xalk.novabee.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class QueenRepository @Inject constructor(private val queenAPI: QueenAPI) {

    private val _queenLiveData = MutableLiveData<NetworkResult<QueenResponse>>()
    val queenLiveData: LiveData<NetworkResult<QueenResponse>>
        get() = _queenLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLiveData

    suspend fun getQueen(apiaryId: String, beehiveId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = queenAPI.getQueen(apiaryId, beehiveId)
        if (response.isSuccessful && response.body() != null) {
            _queenLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _queenLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _queenLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun createQueen(apiaryId: String, beehiveId: String, queenRequest: QueenRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = queenAPI.createQueen(apiaryId, beehiveId, queenRequest)
        handleResponse(response, "Beehive Created")
    }

    suspend fun updateQueen(apiaryId: String, beehiveId: String, queenRequest: QueenRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = queenAPI.updateQueen(apiaryId, beehiveId, queenRequest)
        handleResponse(response, "Beehive Updated")
    }

    private fun handleResponse(response: Response<QueenResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Success("Something went wrong"))
        }
    }

}