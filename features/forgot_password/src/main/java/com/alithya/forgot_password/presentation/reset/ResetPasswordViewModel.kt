package com.alithya.forgot_password.presentation.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.domain.models.ResetPasswordModel
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

class ResetPasswordViewModel(
    private val useCases: PasswordUseCases,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow<ResetPasswordState>(ResetPasswordState.Idle)
    val state: StateFlow<ResetPasswordState> = _state.asStateFlow()

    fun onEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.OnResetPassword -> resetPasswordRequest(
                event.email,
                event.tempPassword,
                event.newPassword
            )
        }
    }

    private fun resetPasswordRequest(
        email : String,
        tempPassword : String,
        newPassword : String
    ) {
        _state.value = ResetPasswordState.Loading
        try {
            viewModelScope.launch(dispatcher) {
                val form = ResetPasswordModel(
                    email = email,
                    tempPassword = tempPassword,
                    newPassword = newPassword
                )
                when (val response = useCases.resetPassword(form)) {
                    is PasswordStatus.Success -> {
                        _state.value = ResetPasswordState.Success
                    }
                    is PasswordStatus.Error -> {
                        _state.value =
                            ResetPasswordState.Error(response.httpError.getErrorMessage())
                    }
                }
            }
        } catch (e: Exception) {
            _state.value = ResetPasswordState.Error(HttpError.handleException(e).getErrorMessage())
        }
    }
}