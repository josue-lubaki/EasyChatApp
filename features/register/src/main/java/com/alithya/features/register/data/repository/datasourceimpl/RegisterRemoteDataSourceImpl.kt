package com.alithya.features.register.data.repository.datasourceimpl

import com.alithya.common.BuildConfig
import com.alithya.common.data.remote.api.AuthenticationService
import com.alithya.common.utils.Constants.AUTHORIZATION
import com.alithya.common.utils.Constants.BEARER
import com.alithya.common.utils.Constants.X_CSRF_TOKEN
import com.alithya.common.utils.HttpError
import com.alithya.features.register.data.remote.api.RegisterService
import com.alithya.features.register.data.models.RegisterRequest
import com.alithya.features.register.data.repository.datasource.RegisterRemoteDataSource
import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User
import kotlinx.coroutines.coroutineScope

internal class RegisterRemoteDataSourceImpl(
    private val service : RegisterService,
    private val authenticationService: AuthenticationService
) : RegisterRemoteDataSource {

    companion object {
        private const val CLIENT_ID = BuildConfig.CLIENT_ID
        private const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
        private const val ADMIN_USERNAME = BuildConfig.ADMIN_USERNAME
        private const val ADMIN_PASSWORD = BuildConfig.ADMIN_PASSWORD
        private const val GRANT_TYPE = BuildConfig.GRANT_TYPE
    }

    override suspend fun register(user: User): RegisterStatus = coroutineScope {

        try {
            val csfr = getCsfrToken() ?: return@coroutineScope RegisterStatus.Error(HttpError.CSRF_TOKEN_MISSING)
            val accessToken = getAccessToken() ?: return@coroutineScope RegisterStatus.Error(HttpError.ACCESS_TOKEN_MISSING)

            val request = RegisterRequest(
                name = listOf("${user.firstname} ${user.lastname}"),
                firstname = listOf(user.firstname),
                lastname = listOf(user.lastname),
                email = listOf(user.email),
                password = listOf(user.password)
            )

            val headers = mapOf(
                X_CSRF_TOKEN to csfr,
                AUTHORIZATION to "$BEARER $accessToken",
            )

            val responseRegister = service.register(
                request = request,
                headers = headers
            )

            return@coroutineScope if (responseRegister.isSuccessful) {
                RegisterStatus.Success
            } else {
                RegisterStatus.Error(HttpError.handleErrorResponse(responseRegister.code()))
            }
        }
        catch (e: Exception) {
            RegisterStatus.Error(HttpError.handleException(e))
        }
    }

    private suspend fun getCsfrToken(): String? {
        val responseCsfrToken = authenticationService.getCsfrToken(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET
        )

        return if (responseCsfrToken.isSuccessful) {
            responseCsfrToken.body()
        } else null
    }

    override suspend fun getAccessToken(): String? {
        val responseAccessToken = authenticationService.getAccessToken(
            grantType = GRANT_TYPE,
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            username = ADMIN_USERNAME,
            password = ADMIN_PASSWORD,
        )

        return if (responseAccessToken.isSuccessful) {
            responseAccessToken.body()?.accessToken.toString()
        } else null
    }
}