package com.example.novabee.ui.login

import android.app.Application
import android.provider.Settings.Global.getString
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novabee.R
import com.example.novabee.models.UserRequest
import com.example.novabee.models.UserResponse
import com.example.novabee.repository.UserRepository
import com.example.novabee.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    application: Application
) : AndroidViewModel(application) {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData


    fun registerUser(userRequest: UserRequest) {

        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }


    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredentials(
        username: String,
        emailAddress: String,
        password: String, isLogin: Boolean
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(emailAddress) ||
            (!isLogin && TextUtils.isEmpty(username)) ||
            TextUtils.isEmpty(password)
        ) {
            result = Pair(false, getApplication<Application>().resources.getString(R.string.txt_valid_all_credentials))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            result = Pair(false, getApplication<Application>().resources.getString(R.string.txt_valid_email))
        } else if (!TextUtils.isEmpty(password) && password.length <= 5) {
            result = Pair(false, getApplication<Application>().resources.getString(R.string.txt_valid_password_length))
        }
        return result

    }

}