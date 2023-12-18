package com.alithya.features.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alithya.common.utils.HttpError
import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User
import com.alithya.features.register.domain.usecases.RegisterUseCase
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

class RegisterViewModel(
    private val useCase: RegisterUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnRegister -> registerRequest(
                firstname = event.firstname,
                lastname = event.lastname,
                email = event.email,
                password = event.password
            )
        }
    }

    private fun registerRequest(
        firstname : String,
        lastname : String,
        email : String,
        password : String
    ) {
        _state.value = RegisterState.Loading
        try {
            viewModelScope.launch(dispatcher) {
                val user =  User(
                    firstname = firstname,
                    lastname = lastname,
                    email = email,
                    password = password
                )

                when (val response = useCase(user)) {
                    is RegisterStatus.Success -> {
                        _state.value = RegisterState.Success
                    }
                    is RegisterStatus.Error -> {
                        _state.value = RegisterState.Error(response.httpError.getErrorMessage())
                    }
                }
            }
        } catch (e: Exception) {
            _state.value = RegisterState.Error(HttpError.handleException(e).getErrorMessage())
        }
    }
}