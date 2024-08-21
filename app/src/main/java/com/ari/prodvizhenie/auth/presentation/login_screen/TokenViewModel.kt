package com.ari.prodvizhenie.auth.presentation.login_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ari.prodvizhenie.auth.data.manager.TokenManager
import com.ari.prodvizhenie.auth.domain.manager.LocalUserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    val accessToken = MutableLiveData("")
    val refreshToken = MutableLiveData("")

    private val _deviceToken = MutableStateFlow("")
    val deviceToken = _deviceToken.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getAccessToken().collect {
                withContext(Dispatchers.Main) {
                    accessToken.value = it
                }
            }
            tokenManager.getRefreshToken().collect {
                withContext(Dispatchers.Main) {
                    refreshToken.value = it
                }
            }
            getDeviceToken()
        }
    }

    fun saveAccessToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveAccessToken(token)
        }
    }

    fun deleteAccessToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteAccessToken()
        }
    }

    fun saveRefreshToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveRefreshToken(token)
        }
    }

    fun deleteRefreshToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteRefreshToken()
        }
    }

    private fun getDeviceToken() {
        viewModelScope.launch {
            localUserManager.readDeviceToken().collect {
                _deviceToken.value = it
            }
        }
    }


}