package ca.josuelubaki.easychat

import android.app.Application
import com.alithya.common.di.dataStoreModule
import com.alithya.common.di.networkModule
import com.alithya.common.di.userModule
import com.alithya.features.login.di.loginModule
import com.alithya.features.register.di.registerModule
import com.alithya.profile.di.profileModule
import com.alithya.forgot_password.di.forgotPasswordModule
import com.alithya.settings.di.settingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * created by Josue Lubaki
 * date : 2023-09-07
 * version : 1.0.0
 */

class EasyChatApp : Application() {

        override fun onCreate() {
            super.onCreate()

            startKoin {
                androidContext(this@EasyChatApp)

                modules(
                    listOf(
                        loginModule,
                        registerModule,
                        forgotPasswordModule,
                        userModule,
                        dataStoreModule,
                        networkModule,
                        settingsModule,
                        profileModule
                    )
                )
            }
        }
}