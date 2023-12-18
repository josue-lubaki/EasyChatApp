package com.alithya.features.login.data.api

import com.alithya.common.utils.Endpoints
import com.alithya.features.login.data.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface LoginService {

    @FormUrlEncoded
    @POST(Endpoints.OAUTH_TOKEN)
    suspend fun login(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}