package com.alithya.forgot_password.presentation.reset

import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.TestDispatcherRule
import com.alithya.forgot_password.domain.models.PasswordStatus
import com.alithya.forgot_password.domain.usecases.PasswordUseCases
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ResetPasswordViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val passwordUseCases : PasswordUseCases = mockk()
    private lateinit var resetPasswordViewModel : ResetPasswordViewModel

    // Given
    val email = "email"
    val tempPassword = "toto"
    val newPassword = "password"

    @Before
    fun setup() {

        resetPasswordViewModel = ResetPasswordViewModel(
            useCases = passwordUseCases,
            dispatcher = testDispatcherRule.testDispatcher
        )
    }

    @Test
    fun `initial state`() {
        assertEquals(ResetPasswordState.Idle, resetPasswordViewModel.state.value)
    }

    @Test
    fun `test onConfirmClicked sends Loading state`() {

        // When
        resetPasswordViewModel.onEvent(ResetPasswordEvent.OnResetPassword(
            email = email,
            tempPassword = tempPassword,
            newPassword = newPassword
        ))

        // Then
        assertEquals(ResetPasswordState.Loading, resetPasswordViewModel.state.value)
    }

    @Test
    fun `test onSendResetEmailClicked sends Success state`() {
        // Given
        val expectedStatus = PasswordStatus.Success
        coEvery { passwordUseCases.resetPassword(any()) } returns expectedStatus

        // When
        resetPasswordViewModel.onEvent(ResetPasswordEvent.OnResetPassword(
            email = email,
            tempPassword = tempPassword,
            newPassword = newPassword
        ))

        // Then
        assertEquals(ResetPasswordState.Success, resetPasswordViewModel.state.value)
    }

    @Test
    fun `test onSendResetEmailClicked sends Error state`() {
        // Given
        val expectedStatus = PasswordStatus.Error(HttpError.UNKNOWN)
        coEvery { passwordUseCases.resetPassword(any()) } returns expectedStatus

        // When
        resetPasswordViewModel.onEvent(ResetPasswordEvent.OnResetPassword(
            email = email,
            tempPassword = tempPassword,
            newPassword = newPassword
        ))

        // Then
        assertEquals(
            expectedStatus.httpError.getErrorMessage(),
            (resetPasswordViewModel.state.value as ResetPasswordState.Error).error
        )
    }
}