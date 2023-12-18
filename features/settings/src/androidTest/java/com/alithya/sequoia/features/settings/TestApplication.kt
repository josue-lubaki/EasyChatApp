package com.alithya.sequoia.features.settings

import android.app.Application
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