package com.alithya.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alithya.common.BuildConfig
import com.alithya.common.domain.models.UserStatus
import com.alithya.common.domain.usecases.UseCasesCommon
import com.alithya.common.domain.usecases.get_me_information.GetMeUseCase
import com.alithya.common.utils.Constants
import com.alithya.common.utils.HttpError
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

class ProfileViewModel(
    private val getMeUseCase: GetMeUseCase,
    private val useCasesCommon: UseCasesCommon,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnLoadMyProfile -> getMyProfile()
        }
    }

    private fun getMyProfile() {
        _state.value = ProfileState.Loading
        try {
            viewModelScope.launch(dispatcher) {
                getMeUseCase().collect { response ->
                    when (response) {
                        is UserStatus.Success -> {
                            val user = response.data
                            useCasesCommon.saveCurrentUserIdUseCase(user.id)
                            user.imageUrl?.let { url ->
                                if (url.startsWith(Constants.PREFIX_IMAGE_URL)) {
                                    user.imageUrl = BuildConfig.BASE_URL + url
                                }
                            }
                            _state.value = ProfileState.Success(user)
                        }

                        is UserStatus.Error -> {
                            _state.value = ProfileState.Error(response.httpError.getErrorMessage())
                        }
                    }
                }

            }
        } catch (e: Exception) {
            // handle errors
            _state.value = ProfileState.Error(HttpError.handleException(e).getErrorMessage())
        }
    }
}