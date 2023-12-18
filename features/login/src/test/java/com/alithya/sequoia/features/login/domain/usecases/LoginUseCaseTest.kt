package com.alithya.sequoia.features.login.domain.usecases

import com.alithya.common.utils.HttpError
import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.models.LoginForm
import com.alithya.features.login.domain.repository.LoginRepository
import com.alithya.features.login.domain.usecases.LoginUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginUseCaseTest {

    private val loginRepository: LoginRepository = mockk()
    private val loginUseCase = LoginUseCase(loginRepository)

    @Test
    fun `invoke should return Success when login is successful`() = runTest {
        // Given
        val loginForm = LoginForm(email = "user@example.com", password = "password")
        val loginStatus = LoginStatus.Success
        coEvery { loginRepository.login(loginForm) } returns loginStatus

        // When
        val result = loginUseCase(loginForm)

        // Then
        coVerify { loginRepository.login(loginForm) }
        assertEquals(loginStatus, result)
    }

    @Test
    fun `invoke should return Error when login fails`() = runTest {
        // Given
        val loginForm = LoginForm(email = "user@example.com", password = "password")
        val exception = HttpError.INTERNET_CONNECTION
        val loginStatus = LoginStatus.Error(HttpError.INTERNET_CONNECTION)
        coEvery { loginRepository.login(loginForm) } returns LoginStatus.Error(exception)

        // When
        val result = loginUseCase(loginForm)

        // Then
        coVerify { loginRepository.login(loginForm) }
        assertEquals(loginStatus, result)
    }
}