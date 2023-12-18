package com.alithya.forgot_password.domain.usecases.forgot

import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.domain.models.PasswordStatus
import com.alithya.forgot_password.domain.repository.ForgotPasswordRepository
import com.alithya.forgot_password.domain.usecases.ForgotPasswordUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForgotPasswordUseCaseTest {

    private val forgotPasswordRepository: ForgotPasswordRepository = mockk()
    private val forgotPasswordUseCase = ForgotPasswordUseCase(forgotPasswordRepository)

    @Test
    fun `invoke should return Success when forgot password is successful`() = runTest {
        // given
        val email = "email"
        val expectedStatus = PasswordStatus.Success
        coEvery { forgotPasswordRepository.forgotPassword(email) } returns expectedStatus

        // when
        val actualStatus = forgotPasswordUseCase(email)

        // then
        assertEquals(expectedStatus, actualStatus)
    }

    @Test
    fun `invoke should return Failure when forgot password fails`() = runTest {
        // given
        val email = "email"
        val expectedStatus = PasswordStatus.Error(HttpError.UNKNOWN)
        coEvery { forgotPasswordRepository.forgotPassword(email) } returns expectedStatus

        // when
        val actualStatus = forgotPasswordUseCase(email)

        // then
        assertEquals(expectedStatus, actualStatus)
        assertEquals(
            expectedStatus.httpError.getErrorMessage(),
            (actualStatus as PasswordStatus.Error).httpError.getErrorMessage()
        )
    }
}