package com.alithya.forgot_password.presentation.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.domain.models.PasswordStatus
import com.alithya.forgot_password.domain.usecases.PasswordUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineDispatcher

/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

class ForgotPasswordViewModel(
    private val useCases: PasswordUseCases,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.OnForgotPassword -> forgotPasswordRequest(event.email)
        }
    }

    private fun forgotPasswordRequest(email : String) {
        _state.value = ForgotPasswordState.Loading
        try {
            viewModelScope.launch(dispatcher) {
                when (val response = useCases.forgotPassword(email)) {
                    is PasswordStatus.Success -> {
                        _state.value = ForgotPasswordState.Success
                    }
                    is PasswordStatus.Error -> {
                        _state.value =
                            ForgotPasswordState.Error(response.httpError.getErrorMessage())
                    }
                }
            }
        } catch (e: Exception) {
            _state.value = ForgotPasswordState.Error(HttpError.handleException(e).getErrorMessage())
        }
    }
}