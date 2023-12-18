package com.alithya.common.data.remote.api

import ca.alithya.sequoia.common.data.models.TokenResponse
import com.alithya.common.utils.Constants.CONTENT_TYPE
import com.alithya.common.utils.Constants.TEXT_PLAIN
import com.alithya.common.utils.Endpoints
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthenticationService {
    @Headers(
        "$CONTENT_TYPE: $TEXT_PLAIN"
    )
    @FormUrlEncoded
    @POST(Endpoints.SESSION_TOKEN)
    suspend fun getCsfrToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ) : Response<String>

    @FormUrlEncoded
    @POST(Endpoints.OAUTH_TOKEN)
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("username") username: String,
        @Field("password") password: String
    ) : Response<TokenResponse>
}