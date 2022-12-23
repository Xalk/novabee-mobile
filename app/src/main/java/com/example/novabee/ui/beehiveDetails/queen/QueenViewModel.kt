package com.example.novabee.ui.beehiveDetails.queen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

}