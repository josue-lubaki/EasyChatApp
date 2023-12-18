package com.alithya.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alithya.common.domain.usecases.UseCasesCommon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class SettingViewModel(
    private val useCasesCommon : UseCasesCommon,
    private val dispatchers: CoroutineDispatcher
) : ViewModel() {

    private val _preferredLanguage : MutableStateFlow<String> = MutableStateFlow(Locale.getDefault().language)
    val preferredLanguage : StateFlow<String> = _preferredLanguage.asStateFlow()

    init {
        viewModelScope.launch(dispatchers) {
            _preferredLanguage.value =
                useCasesCommon.readPreferredLanguageUseCase().stateIn(viewModelScope).value
        }
    }

    fun savePreferredLanguage(language : String) {
        viewModelScope.launch(dispatchers) {
            _preferredLanguage.value = language
            useCasesCommon.savePreferredLanguageUseCase(language)
        }
    }
}