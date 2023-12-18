package com.alithya.common.data.datasource.datasourceimpl

import com.alithya.common.data.remote.api.AuthenticationService
import com.alithya.common.data.remote.api.UserService
import com.alithya.common.data.datasource.UserRemoteDataSource
import com.alithya.common.data.models.toDomain
import com.alithya.common.domain.models.UserStatus
import com.alithya.common.BuildConfig
import com.alithya.common.utils.HttpError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


internal class UserRemoteDataSourceImpl(
    private val service : UserService,
    private val authenticationService: AuthenticationService,
    private val dispatchers: CoroutineDispatcher,
) : UserRemoteDataSource {
    override suspend fun getMe() : Flow<UserStatus> {

        return flow {
            try {
                val response = service.getMe()
                when (response.isSuccessful) {
                    true -> {
                        val userInfo = response.body()!!.toDomain()
                        emit(UserStatus.Success(userInfo))
                    }
                    false -> {
                        emit(UserStatus.Error(HttpError.handleErrorResponse(response.code())))
                    }
                }
            } catch (e: Exception) {
                emit(UserStatus.Error(HttpError.handleException(e)))
            }
        }.flowOn(dispatchers)
    }

    private suspend fun getCsrfToken(): String? {
        val responseCsrfToken = authenticationService.getCsfrToken(
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET
        )

        return if (responseCsrfToken.isSuccessful) {
            responseCsrfToken.body()
        } else null
    }

}