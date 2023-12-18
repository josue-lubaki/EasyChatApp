package com.alithya.features.register.data.remote.api

import com.alithya.common.utils.Constants.ACCEPT
import com.alithya.common.utils.Constants.APPLICATION_JSON
import com.alithya.common.utils.Constants.CONTENT_TYPE
import com.alithya.common.utils.Endpoints
import com.alithya.features.register.data.models.RegisterRequest
import com.alithya.features.register.data.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface RegisterService {

    @Headers(
        "$ACCEPT: $APPLICATION_JSON",
        "$CONTENT_TYPE: $APPLICATION_JSON"
    )
    @POST(Endpoints.REGISTER)
    suspend fun register(
        @Body request: RegisterRequest,
        @HeaderMap headers : Map<String, String>
    ): Response<RegisterResponse>

}