package com.alithya.common.network

import com.alithya.common.domain.repository.DataStoreOperations
import com.alithya.common.utils.Constants.AUTHORIZATION
import com.alithya.common.utils.Constants.BEARER
import com.alithya.common.utils.Endpoints
import okhttp3.Interceptor
import okhttp3.Response

/**
 * created by Josue Lubaki
 * date : 2023-04-10
 * version : 1.0.0
 */

class AuthInterceptor(private val dataStoreOperations: DataStoreOperations) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val whiteList = listOf(
            Endpoints.OAUTH_TOKEN,
            Endpoints.REGISTER,
            Endpoints.SESSION_TOKEN
        )

        val path = originalRequest.url.encodedPath
        if (whiteList.contains(path)) {
            return chain.proceed(originalRequest)
        }

        val requestBuilder = originalRequest.newBuilder()
            .header(AUTHORIZATION, "$BEARER ${getToken()}")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun getToken(): String {
        return dataStoreOperations.readAccessToken()
    }
}