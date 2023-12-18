package com.alithya.sequoia.features.login.data.repository

import com.alithya.common.utils.HttpError
import com.alithya.features.login.data.repository.LoginRepositoryImpl
import com.alithya.features.login.data.repository.datasource.LoginRemoteDataSource
import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.models.LoginForm
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginDataStoreRepositoryTest {
    private val loginRemoteDataSource = mockk<LoginRemoteDataSource>()
    private val loginRepository = LoginRepositoryImpl(loginRemoteDataSource)

    @Test
    fun `login() returns success`() = runTest {
        // given
        val loginForm = LoginForm(email = "user@example.com", password = "password")
        val expectedStatus = LoginStatus.Success
        coEvery { loginRemoteDataSource.login(loginForm) } returns expectedStatus

        // when
        val actualStatus = loginRepository.login(loginForm)

        // then
        assertEquals(expectedStatus, actualStatus)
    }

    @Test
    fun `login() returns error`() = runTest {
        // given
        val loginForm = LoginForm(email = "user@example.com", password = "password")
        val expectedStatus = LoginStatus.Error(HttpError.UNKNOWN)
        coEvery { loginRemoteDataSource.login(loginForm) } returns expectedStatus

        // when
        val actualStatus = loginRepository.login(loginForm)

        // then
        assertEquals(expectedStatus, actualStatus)
    }
}