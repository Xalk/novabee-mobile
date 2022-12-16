package com.example.novabee.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.novabee.api.QueenAPI
import com.example.novabee.models.QueenResponse
import com.example.novabee.utils.NetworkResult
import org.json.JSONObject
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

}