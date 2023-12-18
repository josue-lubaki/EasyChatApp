package com.alithya.profile.di

import com.alithya.profile.presentation.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel {
        ProfileViewModel(
            dispatcher = get(),
            useCasesCommon = get(),
            getMeUseCase = get(),
        )
    }
}