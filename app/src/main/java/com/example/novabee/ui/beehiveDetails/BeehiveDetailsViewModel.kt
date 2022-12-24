package com.example.novabee.ui.beehiveDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novabee.models.BeehiveRequest
import com.example.novabee.repository.BeehiveDetailsRepository
import com.example.novabee.repository.BeehiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeehiveDetailsViewModel @Inject constructor(private val beehiveDetailsRepository: BeehiveDetailsRepository) :
    ViewModel() {

    val oneBeehiveLiveData get() = beehiveDetailsRepository.oneBeehiveLiveData
    val statusLiveData get() = beehiveDetailsRepository.statusLiveData

    fun getBeehive(apiaryId: String, beehiveId: String) {
        viewModelScope.launch {
            beehiveDetailsRepository.getBeehive(apiaryId, beehiveId)
        }
    }

    fun createBeehive(apiaryId: String, beehiveRequest: BeehiveRequest) {
        viewModelScope.launch {
            beehiveDetailsRepository.createBeehive(apiaryId, beehiveRequest)
        }
    }
    fun updateBeehive(apiaryId: String, beehiveId: String,  beehiveRequest: BeehiveRequest){
        viewModelScope.launch {
            beehiveDetailsRepository.updateBeehive(apiaryId, beehiveId, beehiveRequest)
        }
    }

    fun deleteBeehive(apiaryId: String, beehiveId: String) {
        viewModelScope.launch {
            beehiveDetailsRepository.deleteBeehive(apiaryId, beehiveId)
        }
    }

}