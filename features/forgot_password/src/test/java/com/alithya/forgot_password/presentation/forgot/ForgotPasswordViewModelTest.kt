package com.alithya.forgot_password.presentation.forgot

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

class ForgotPasswordViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val passwordUseCases : PasswordUseCases = mockk()
    private lateinit var forgotPasswordViewModel : ForgotPasswordViewModel

    @Before
    fun setup() {

        forgotPasswordViewModel = ForgotPasswordViewModel(
            useCases = passwordUseCases,
            dispatcher = testDispatcherRule.testDispatcher
        )
    }

    @Test
    fun `initial state`() {
        assertEquals(ForgotPasswordState.Idle, forgotPasswordViewModel.state.value)
    }

    @Test
    fun `test onSendResetEmailClicked sends Loading state`() {
        // Given
        val email = "email"

        // When
        forgotPasswordViewModel.onEvent(ForgotPasswordEvent.OnForgotPassword(email))

        // Then
        assertEquals(ForgotPasswordState.Loading, forgotPasswordViewModel.state.value)
    }

    @Test
    fun `test onSendResetEmailClicked sends Success state`() {
        // Given
        val email = "email"
        val expectedStatus = PasswordStatus.Success
        coEvery { passwordUseCases.forgotPassword(email) } returns expectedStatus

        // When
        forgotPasswordViewModel.onEvent(ForgotPasswordEvent.OnForgotPassword(email))

        // Then
        assertEquals(ForgotPasswordState.Success, forgotPasswordViewModel.state.value)
    }

    @Test
    fun `test onSendResetEmailClicked sends Error state`() {
        // Given
        val email = "email"
        val expectedStatus = PasswordStatus.Error(HttpError.UNKNOWN)
        coEvery { passwordUseCases.forgotPassword(email) } returns expectedStatus

        // When
        forgotPasswordViewModel.onEvent(ForgotPasswordEvent.OnForgotPassword(email))

        // Then
        assertEquals(
            expectedStatus.httpError.getErrorMessage(),
            (forgotPasswordViewModel.state.value as ForgotPasswordState.Error).error
        )
    }
}