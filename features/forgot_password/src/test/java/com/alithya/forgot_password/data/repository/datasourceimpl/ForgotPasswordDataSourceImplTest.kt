package com.alithya.forgot_password.data.repository.datasourceimpl

import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.data.api.ForgotPasswordService
import com.alithya.forgot_password.data.models.ForgotPasswordResponse
import com.alithya.forgot_password.domain.models.PasswordStatus
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ForgotPasswordDataSourceImplTest {

    private val forgotPasswordService : ForgotPasswordService = mockk()
    private val forgotPasswordRemoteDataSourceImpl = ForgotPasswordRemoteDataSourceImpl(forgotPasswordService)

    @Test
    fun `forgotPassword() returns success`() = runTest {
        // Given
        val expectedStatus = mockk<Response<ForgotPasswordResponse>>()
        coEvery { expectedStatus.isSuccessful } returns true
        coEvery { forgotPasswordService.forgotPassword(any()) } returns expectedStatus

        // When
        val actualStatus = forgotPasswordRemoteDataSourceImpl.forgotPassword("email")

        // Then
        assertEquals(PasswordStatus.Success, actualStatus)
    }

    @Test
    fun `forgotPassword() returns failure`() = runTest {
        // Given
        val message = "An error occurred"
        val errorMessage = ForgotPasswordResponse(message)
        val expectedStatus = mockk<Response<ForgotPasswordResponse>>()
        coEvery { expectedStatus.isSuccessful } returns false
        coEvery { expectedStatus.code() } returns 500
        coEvery { expectedStatus.body() } returns errorMessage
        coEvery { expectedStatus.message() } returns message
        coEvery { forgotPasswordService.forgotPassword(any()) } returns expectedStatus

        // When
        val actualStatus = forgotPasswordRemoteDataSourceImpl.forgotPassword("email")

        // Then
        assertEquals(PasswordStatus.Error(HttpError.UNKNOWN)::class.java, actualStatus::class.java)
        assertEquals(
            PasswordStatus.Error(HttpError.UNKNOWN).httpError.getErrorMessage(),
            (actualStatus as PasswordStatus.Error).httpError.getErrorMessage()
        )
    }

    @Test
    fun `forgotPassword() returns error with code 400`() = runTest {
        // Given
        val message = HttpError.BAD_REQUEST.getErrorMessage().toString()
        val errorMessage = ForgotPasswordResponse(message)
        val responseBody = Gson().toJson(errorMessage).toResponseBody("application/json".toMediaTypeOrNull())
        val expectedStatus = mockk<Response<ForgotPasswordResponse>>()
        coEvery { expectedStatus.isSuccessful } returns false
        coEvery { expectedStatus.code() } returns 400
        coEvery { expectedStatus.errorBody() } returns responseBody
        coEvery { forgotPasswordService.forgotPassword(any()) } returns expectedStatus
        coEvery { forgotPasswordService.resetPassword(any()) } returns expectedStatus

        // When
        val actualStatus = forgotPasswordRemoteDataSourceImpl.forgotPassword("email")

        // Then
        assertEquals(PasswordStatus.Error(HttpError.BAD_REQUEST)::class.java, actualStatus::class.java)
        assertEquals(
            PasswordStatus.Error(HttpError.BAD_REQUEST).httpError.getErrorMessage(),
            (actualStatus as PasswordStatus.Error).httpError.getErrorMessage()
        )
    }
}