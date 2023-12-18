package com.alithya.sequoia.features.login.data.repository.datasourceimpl

import com.alithya.common.domain.usecases.UseCasesCommon
import com.alithya.features.login.data.api.LoginService
import com.alithya.features.login.data.models.LoginResponse
import com.alithya.features.login.data.repository.datasourceimpl.LoginRemoteDataSourceImpl
import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.models.LoginForm
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LoginRemoteDataSourceImplTest {

    private val mockLoginService = mockk<LoginService>()
    private val mockUseCasesCommon = mockk<UseCasesCommon>()

    private val loginRemoteDataSource = LoginRemoteDataSourceImpl(
        service = mockLoginService,
        useCasesCommon = mockUseCasesCommon
    )

    @Test
    fun testLoginSuccessful() = runTest {
        // Given
        val loginForm = LoginForm(email = "test@test.com", password = "password")
        val expectedResponse = Response.success(
            LoginResponse(
                tokenType = "tokenType",
                accessToken = "access_token",
                refreshToken = "refresh_token",
                expiresIn = 3600
            )
        )

        coEvery { mockLoginService.login(any(), any(), any(), any(), any()) } returns expectedResponse
        coEvery { mockUseCasesCommon.saveAccessTokenUseCase(any()) } returns Unit

        // When
        val result = loginRemoteDataSource.login(loginForm)

        // Then
        assertTrue(result is LoginStatus.Success)
    }

    @Test
    fun testLoginFailed() = runTest {
        // Given
        val loginForm = LoginForm(email = "test@test.com", password = "password")
        val expectedResponse = Response.error<LoginResponse>(
            401, "".toResponseBody("application/json".toMediaTypeOrNull())
        )

        coEvery { mockLoginService.login(any(), any(), any(), any(), any()) } returns expectedResponse

        // When
        val result = loginRemoteDataSource.login(loginForm)

        // Then
        assertTrue(result is LoginStatus.Error)
    }
}