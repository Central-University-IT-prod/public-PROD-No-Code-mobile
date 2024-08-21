package com.ari.prodvizhenie.auth.presentation.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ari.prodvizhenie.auth.domain.manager.LocalUserManager
import com.ari.prodvizhenie.auth.domain.repository.LoginRepository
import com.ari.prodvizhenie.auth.presentation.util.sendEvent
import com.ari.prodvizhenie.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    private val _state = MutableStateFlow(LoginViewState())
    val state = _state.asStateFlow()

    private val _deviceToken = MutableStateFlow("")
    val deviceToken = _deviceToken.asStateFlow()

    init {
        getToken()
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loginRepository.login(username = username, password = password)
                .onRight { response ->
                    _state.update {
                        it.copy(tokens = response.data)
                    }
                }.onLeft { error ->
                    Log.d("TAG", "loginUser: $error")
                    _state.update {
                        it.copy(
                            error = error.error.message
                        )
                    }
                    sendEvent(Event.Toast(error.error.message))
                }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun saveAppEntry() {
        viewModelScope.launch {
            localUserManager.saveAppEntry()
        }
    }

    fun deleteAppEntry() {
        viewModelScope.launch {
            localUserManager.deleteAppEntry()
        }
    }

    private fun getToken() {
        viewModelScope.launch {
            localUserManager.readDeviceToken().collect {
                _deviceToken.value = it
            }
        }
    }

}