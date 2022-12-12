package com.example.novabee.ui.apiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novabee.models.ApiaryRequest
import com.example.novabee.repository.ApiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApiaryViewModel @Inject constructor(private val  apiaryRepository: ApiaryRepository) : ViewModel() {

    val apiaryLiveData get() = apiaryRepository.apiaryLiveData
    val statusLiveData get() = apiaryRepository.statusLiveData


    fun getApiaries(){
        viewModelScope.launch {
            apiaryRepository.getApiaries()
        }
    }
    fun createApiary(apiaryRequest: ApiaryRequest){
        viewModelScope.launch {
            apiaryRepository.createApiary(apiaryRequest)
        }
    }

    fun updateApiary(apiaryId: String, apiaryRequest: ApiaryRequest){
        viewModelScope.launch {
            apiaryRepository.updateApiary(apiaryId, apiaryRequest)
        }
    }

    fun deleteApiary(apiaryId: String){
        viewModelScope.launch {
            apiaryRepository.deleteApiary(apiaryId)
        }
    }


}