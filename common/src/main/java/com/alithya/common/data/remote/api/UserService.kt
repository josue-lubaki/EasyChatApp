package com.alithya.common.data.remote.api

import com.alithya.common.data.models.ApiResponse
import com.alithya.common.utils.Constants.ACCEPT
import com.alithya.common.utils.Constants.APPLICATION_JSON
import com.alithya.common.utils.Constants.APPLICATION_VND_API_JSON
import com.alithya.common.utils.Constants.CONTENT_TYPE
import com.alithya.common.utils.Endpoints
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    @Headers(
        "$ACCEPT: $APPLICATION_JSON",
        "$CONTENT_TYPE: $APPLICATION_VND_API_JSON"
    )
    @POST(Endpoints.API_ME)
    suspend fun getMe() : Response<ApiResponse>
}