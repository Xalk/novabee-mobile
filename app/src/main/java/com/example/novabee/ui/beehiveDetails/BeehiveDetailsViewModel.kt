package com.example.novabee.ui.beehiveDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novabee.repository.BeehiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeehiveDetailsViewModel @Inject constructor(private val beehiveRepository: BeehiveRepository) :
    ViewModel() {

    val beehiveLiveData get() = beehiveRepository.beehiveInfoLiveData
    val statusLiveData get() = beehiveRepository.statusLiveData

    fun getBeehive(apiaryId: String, beehiveId: String) {
        viewModelScope.launch {
            beehiveRepository.getBeehive(apiaryId, beehiveId)
        }
    }

}