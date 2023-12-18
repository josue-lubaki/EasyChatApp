package com.alithya.forgot_password.data.api

import com.alithya.common.utils.Endpoints
import com.alithya.forgot_password.data.models.ForgotPasswordRequest
import com.alithya.forgot_password.data.models.ForgotPasswordResetRequest
import com.alithya.forgot_password.data.models.ForgotPasswordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface ForgotPasswordService {

    @POST(Endpoints.LOST_PASSWORD)
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ) : Response<ForgotPasswordResponse>


    @POST(Endpoints.LOST_PASSWORD_RESET)
    suspend fun resetPassword(
        @Body request: ForgotPasswordResetRequest
    ) : Response<ForgotPasswordResponse>
}