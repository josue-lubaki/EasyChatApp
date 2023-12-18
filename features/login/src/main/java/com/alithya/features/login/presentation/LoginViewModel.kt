package com.alithya.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alithya.common.utils.HttpError
import com.alithya.features.login.domain.models.LoginForm
import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineDispatcher

/**
 * created by josuelubaki
 * date : 2023-10-04
 * version : 1.0.0
 */

class LoginViewModel(
    private val useCase: LoginUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLogin -> loginRequest(event.email, event.password)
        }
    }

    private fun loginRequest(email : String, password : String) {
        _state.value = LoginState.Loading
        try {
            viewModelScope.launch(dispatcher) {
                when (val response = useCase(LoginForm(email, password))) {
                    is LoginStatus.Success -> {
                        _state.value = LoginState.Success
                    }
                    is LoginStatus.Error -> {
                        _state.value = LoginState.Error(response.httpError.getErrorMessage())
                    }
                }
            }
        } catch (e: Exception) {
            _state.value = LoginState.Error(HttpError.handleException(e).getErrorMessage())
        }
    }
}

