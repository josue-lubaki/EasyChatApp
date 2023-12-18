package com.alithya.sequoia.features.login.presentation

import com.alithya.common.utils.HttpError
import com.alithya.sequoia.features.login.TestDispatcherRule
import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.models.LoginForm
import com.alithya.features.login.domain.usecases.LoginUseCase
import com.alithya.features.login.presentation.LoginEvent
import com.alithya.features.login.presentation.LoginState
import com.alithya.features.login.presentation.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    // Create a mock of LoginUseCase
    private val mockLoginUseCase = mockk<LoginUseCase>()

    // Create a LoginViewModel instance
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(
            useCase = mockLoginUseCase,
            dispatcher = testDispatcherRule.testDispatcher
        )
    }

    @Test
    fun testInitialState() = runTest {
        assertEquals(LoginState.Idle, loginViewModel.state.value)
    }

    @Test
    fun `test onLoginClicked sends Loading state`() {
        // When
        loginViewModel.onEvent(LoginEvent.OnLogin("test@example.com", "password"))

        // Then
        assertEquals(LoginState.Loading, loginViewModel.state.value)
    }

    @Test
    fun `test onLoginClicked sends Success state`() {
        // Given
        val email = "test@example.com"
        val password = "password"
        val expectedStatus = LoginStatus.Success
        coEvery { mockLoginUseCase(LoginForm(email, password)) } returns expectedStatus

        // When
        loginViewModel.onEvent(LoginEvent.OnLogin(email, password))

        // Then
        assertEquals(LoginState.Success, loginViewModel.state.value)
    }

    @Test
    fun `test onLoginClicked sends Error state`() = runTest {
        // Given
        val exception = HttpError.UNKNOWN
        val expectedStatus = LoginStatus.Error(exception)
        coEvery { mockLoginUseCase.invoke(any()) } returns expectedStatus

        // When
        loginViewModel.onEvent(LoginEvent.OnLogin("test@example.com", "password"))

        // Then
        assertEquals(LoginState.Error(exception.getErrorMessage()), loginViewModel.state.value)
        assertEquals(
            expectedStatus.httpError.getErrorMessage(),
            (loginViewModel.state.value as LoginState.Error).message
        )
    }

}