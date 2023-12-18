package com.alithya.settings.di

import com.alithya.settings.presentation.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel {
        SettingViewModel(
            useCasesCommon = get(),
            dispatchers = get()
        )
    }
}