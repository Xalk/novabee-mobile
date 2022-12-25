package com.example.novabee.ui.beehiveDetails.queen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novabee.models.QueenRequest
import com.example.novabee.repository.QueenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueenViewModel @Inject constructor(private val queenRepository: QueenRepository) :
    ViewModel() {

    val queenLiveData get() = queenRepository.queenLiveData
    val statusLiveData get() = queenRepository.statusLiveData

    fun getQueen(apiaryId: String, beehiveId: String) {
        viewModelScope.launch {
            queenRepository.getQueen(apiaryId, beehiveId)
        }
    }

    fun createQueen(apiaryId: String, beehiveId: String, queenRequest: QueenRequest) {
        viewModelScope.launch {
            queenRepository.createQueen(apiaryId, beehiveId, queenRequest)
        }
    }
    fun updateQueen(apiaryId: String, beehiveId: String, queenRequest: QueenRequest){
        viewModelScope.launch {
            queenRepository.updateQueen(apiaryId, beehiveId, queenRequest)
        }
    }

}