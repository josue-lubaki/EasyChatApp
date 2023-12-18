package com.alithya.features.login.data.repository.datasourceimpl

import com.alithya.common.BuildConfig
import com.alithya.common.domain.usecases.UseCasesCommon
import com.alithya.common.utils.HttpError
import com.alithya.features.login.data.api.LoginService
import com.alithya.features.login.data.repository.datasource.LoginRemoteDataSource
import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.models.LoginForm

internal class LoginRemoteDataSourceImpl(
    private val service: LoginService,
    private val useCasesCommon: UseCasesCommon
) : LoginRemoteDataSource {

    companion object {
        private const val GRANT_TYPE = BuildConfig.GRANT_TYPE
        private const val CLIENT_ID = BuildConfig.CLIENT_ID
        private const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
    }

    override suspend fun login(loginForm: LoginForm): LoginStatus {
        try {
            val responseLogin = service.login(
                grantType = GRANT_TYPE,
                clientId = CLIENT_ID,
                clientSecret = CLIENT_SECRET,
                username = loginForm.email,
                password = loginForm.password
            )

            return if (responseLogin.isSuccessful) {
                useCasesCommon.saveAccessTokenUseCase(responseLogin.body()?.accessToken.orEmpty())
                LoginStatus.Success
            } else {
                LoginStatus.Error(HttpError.handleErrorResponse(responseLogin.code()))
            }
        } catch (e: Exception) {
            return LoginStatus.Error(HttpError.handleException(e))
        }
    }
}