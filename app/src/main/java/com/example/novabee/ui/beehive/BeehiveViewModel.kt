package com.example.novabee.ui.beehive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novabee.models.BeehiveRequest
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.repository.BeehiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeehiveViewModel  @Inject constructor(private val beehiveRepository: BeehiveRepository): ViewModel(){

    val beehiveLiveData get() = beehiveRepository.beehiveLiveData
    val beehiveInfoLiveData get() = beehiveRepository.beehiveInfoLiveData

    val statusLiveData get() = beehiveRepository.statusLiveData

    fun getBeehives(apiaryId: String){
        viewModelScope.launch {
            beehiveRepository.getBeehives(apiaryId)
        }
    }

    fun createBeehive(apiaryId: String, beehiveRequest: BeehiveRequest) {
        viewModelScope.launch {
            beehiveRepository.createBeehive(apiaryId, beehiveRequest)
        }
    }
    fun updateBeehive(apiaryId: String, beehiveId: String,  beehiveRequest: BeehiveRequest){
        viewModelScope.launch {
            beehiveRepository.updateBeehive(apiaryId, beehiveId, beehiveRequest)
        }
    }

    fun deleteBeehive(apiaryId: String, beehiveId: String) {
        viewModelScope.launch {
            beehiveRepository.deleteBeehive(apiaryId, beehiveId)
        }
    }

    suspend fun getBeehive(apiaryId: String, beehiveId: String) {
        viewModelScope.launch {
            beehiveRepository.getBeehive(apiaryId, beehiveId)
        }
    }

}