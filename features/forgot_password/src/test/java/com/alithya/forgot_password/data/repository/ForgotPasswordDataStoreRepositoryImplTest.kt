package com.alithya.forgot_password.data.repository

import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.data.repository.datasource.ForgotPasswordRemoteDataSource
import com.alithya.forgot_password.domain.models.PasswordStatus
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForgotPasswordDataStoreRepositoryImplTest {

    private val forgotPasswordRemoteDataSource = mockk<ForgotPasswordRemoteDataSource>()
    private val forgotPasswordRepository =
        ForgotPasswordRepositoryImpl(forgotPasswordRemoteDataSource)

    @Test
    fun `forgotPassword() returns success`() = runTest {
        // given
        val email = "email"
        val expectedStatus = PasswordStatus.Success
        coEvery { forgotPasswordRemoteDataSource.forgotPassword(email) } returns expectedStatus

        // when
        val actualStatus = forgotPasswordRepository.forgotPassword(email)

        // then
        assertEquals(expectedStatus, actualStatus)
    }

    @Test
    fun `forgotPassword() returns error`() = runTest {
        // given
        val email = "email"
        val expectedStatus = PasswordStatus.Error(HttpError.UNKNOWN)
        coEvery { forgotPasswordRemoteDataSource.forgotPassword(email) } returns expectedStatus

        // when
        val actualStatus = forgotPasswordRepository.forgotPassword(email)

        // then
        assertEquals(expectedStatus, actualStatus)
        assertEquals(
            expectedStatus.httpError.getErrorMessage(),
            (actualStatus as PasswordStatus.Error).httpError.getErrorMessage()
        )
    }
}