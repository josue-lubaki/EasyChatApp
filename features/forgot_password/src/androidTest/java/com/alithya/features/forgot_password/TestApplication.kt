package com.alithya.features.forgot_password

import android.app.Application
import com.alithya.common.di.dataStoreModule
import com.alithya.common.di.networkModule
import com.alithya.common.di.userModule
import com.alithya.forgot_password.di.forgotPasswordModule
import org.koin.core.context.startKoin

/**
 * created by Josue Lubaki
 * date : 2023-09-11
 * version : 1.0.0
 */

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {}
    }
}