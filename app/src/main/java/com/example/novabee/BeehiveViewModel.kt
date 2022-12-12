package com.example.novabee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novabee.repository.BeehiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeehiveViewModel  @Inject constructor(private val beehiveRepository: BeehiveRepository): ViewModel(){

    val beehiveLiveData get() = beehiveRepository.beehiveLiveData
    val statusLiveDara get() = beehiveRepository.statusLiveData

    fun getBeehives(apiaryId: String){
        viewModelScope.launch {
            beehiveRepository.getBeehives(apiaryId)
        }
    }

}