package com.alithya.features.register.data.repository.datasourceimpl

import com.alithya.features.register.data.remote.api.RegisterService
import com.alithya.features.register.data.models.RegisterResponse
import ca.alithya.sequoia.common.data.models.TokenResponse
import com.alithya.common.data.remote.api.AuthenticationService
import com.alithya.features.register.data.repository.datasourceimpl.RegisterRemoteDataSourceImpl
import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterRemoteDataSourceImplTest {

    private val mockRegisterService = mockk<RegisterService>()
    private val authenticationService : AuthenticationService = mockk()
    private val registerRemoteDataSource = RegisterRemoteDataSourceImpl(
        service = mockRegisterService,
        authenticationService = authenticationService
    )

    @Before
    fun setUp() {
        coEvery { authenticationService.getCsfrToken(any(), any()) } returns Response.success("token")
        coEvery { authenticationService.getAccessToken(any(), any(), any(), any(), any()) } returns Response.success(
            TokenResponse(
                accessToken = "token",
                refreshToken = "refresh_token",
                tokenType = "bearer",
                expiresIn = 3600
            )
        )
    }

    @Test
    fun testRegisterSuccessful() = runTest {
        // Given
        val user = User(
            firstname = "firstName",
            lastname = "lastName",
            email = "firstName.lastName@example.com",
            password = "password",
        )
        val expectedResponse = mockk<Response<RegisterResponse>>()
        coEvery { expectedResponse.isSuccessful } returns true
        coEvery { mockRegisterService.register(any(), any()) } returns expectedResponse

        // When
        val actualResponse = registerRemoteDataSource.register(user)

        // Then
        assertTrue(actualResponse is RegisterStatus.Success)
    }

    @Test
    fun testRegisterError() = runTest {
        // Given
        val user = User(
            firstname = "firstName",
            lastname = "lastName",
            email = "firstName.lastName@example.com",
            password = "password",
        )
        val expectedResponse = Response.error<RegisterResponse>(
            400,
            "Bad Request".toResponseBody("application/json".toMediaTypeOrNull())
        )
        coEvery { mockRegisterService.register(any(), any()) } returns expectedResponse

        // When
        val actualResponse = registerRemoteDataSource.register(user)

        // Then
        assertTrue(actualResponse is RegisterStatus.Error)
    }
}