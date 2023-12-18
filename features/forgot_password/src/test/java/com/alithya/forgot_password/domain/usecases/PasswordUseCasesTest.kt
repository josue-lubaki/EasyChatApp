package com.alithya.forgot_password.domain.usecases

import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.domain.models.ResetPasswordModel
import com.alithya.forgot_password.domain.models.PasswordStatus
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PasswordUseCasesTest {

    private val forgotPasswordUseCase: ForgotPasswordUseCase = mockk()
    private val resetPasswordUseCase: ResetPasswordUseCase = mockk()

    private val passwordUseCases = PasswordUseCases(
        forgotPasswordUseCase,
        resetPasswordUseCase
    )

    @Test
    fun `forgotPasswordUseCases should return the same success value as the one returned by forgotPasswordUseCase use case`() =
        runTest {
            // given
            val email = "email"
            val expectedStatus = PasswordStatus.Success
            coEvery { forgotPasswordUseCase(email) } returns expectedStatus

            // when
            val actualStatus = passwordUseCases.forgotPassword(email)

            // then
            assertEquals(expectedStatus, actualStatus)
        }

    @Test
    fun `forgotPasswordUseCases should return the same success value as the one returned by forgotPasswordResetUseCase use case`() =
        runTest {
            // given
            val resetModel = ResetPasswordModel(
                email = "email",
                tempPassword = "tempPassword",
                newPassword = "newPassword"
            )
            val expectedStatus = PasswordStatus.Success
            coEvery { resetPasswordUseCase(resetModel) } returns expectedStatus

            // when
            val actualStatus = passwordUseCases.resetPassword(resetModel)

            // then
            assertEquals(expectedStatus, actualStatus)
        }

    @Test
    fun `forgotPasswordUseCases should return the same failure value as the one returned by forgotPasswordUseCase use case`() =
        runTest {
            // given
            val email = "email"
            val expectedStatus = PasswordStatus.Error(HttpError.UNKNOWN)
            coEvery { forgotPasswordUseCase(email) } returns expectedStatus

            // when
            val actualStatus = passwordUseCases.forgotPassword(email)

            // then
            assertEquals(expectedStatus, actualStatus)
            assertEquals(
                expectedStatus.httpError.getErrorMessage(),
                (actualStatus as PasswordStatus.Error).httpError.getErrorMessage()
            )
        }

    @Test
    fun `forgotPasswordUseCases should return the same failure value as the one returned by forgotPasswordResetUseCase use case`() =
        runTest {
            // given
            val resetModel = ResetPasswordModel(
                email = "email",
                tempPassword = "tempPassword",
                newPassword = "newPassword"
            )
            val expectedStatus = PasswordStatus.Error(HttpError.UNKNOWN)
            coEvery { resetPasswordUseCase(resetModel) } returns expectedStatus

            // when
            val actualStatus = passwordUseCases.resetPassword(resetModel)

            // then
            assertEquals(expectedStatus, actualStatus)
            assertEquals(
                expectedStatus.httpError.getErrorMessage(),
                (actualStatus as PasswordStatus.Error).httpError.getErrorMessage()
            )
        }
}