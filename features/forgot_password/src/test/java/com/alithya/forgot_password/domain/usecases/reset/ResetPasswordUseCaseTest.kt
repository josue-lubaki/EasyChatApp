package com.alithya.forgot_password.domain.usecases.reset

import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.domain.models.ResetPasswordModel
import com.alithya.forgot_password.domain.models.PasswordStatus
import com.alithya.forgot_password.domain.repository.ForgotPasswordRepository
import com.alithya.forgot_password.domain.usecases.ResetPasswordUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResetPasswordUseCaseTest {

    private val forgotPasswordRepository: ForgotPasswordRepository = mockk()
    private val resetPasswordUseCase = ResetPasswordUseCase(forgotPasswordRepository)

    @Test
    fun `invoke should return Success when forgot password is successful`() = runTest {
        // given
        val resetModel = ResetPasswordModel(
            email = "email",
            tempPassword = "tempPassword",
            newPassword = "newPassword"
        )
        val expectedStatus = PasswordStatus.Success
        coEvery { forgotPasswordRepository.resetPassword(resetModel) } returns expectedStatus

        // when
        val actualStatus = resetPasswordUseCase(resetModel)

        // then
        assertEquals(expectedStatus, actualStatus)
    }

    @Test
    fun `invoke should return Failure when forgot password fails`() = runTest {
        // given
        val resetModel = ResetPasswordModel(
            email = "email",
            tempPassword = "tempPassword",
            newPassword = "newPassword"
        )
        val expectedStatus = PasswordStatus.Error(HttpError.UNKNOWN)
        coEvery { forgotPasswordRepository.resetPassword(resetModel) } returns expectedStatus

        // when
        val actualStatus = resetPasswordUseCase(resetModel)

        // then
        assertEquals(expectedStatus, actualStatus)
        assertEquals(
            expectedStatus.httpError.getErrorMessage(),
            (actualStatus as PasswordStatus.Error).httpError.getErrorMessage()
        )
    }
}