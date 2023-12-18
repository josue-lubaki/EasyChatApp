package com.alithya.features.register.presentation

import com.alithya.common.utils.HttpError
import com.alithya.features.register.TestDispatcherRule
import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User
import com.alithya.features.register.domain.usecases.RegisterUseCase
import com.alithya.features.register.presentation.RegisterState
import com.alithya.features.register.presentation.RegisterViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val mockRegisterUseCase = mockk<RegisterUseCase>()

    private lateinit var registerViewModel: RegisterViewModel


    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(
            useCase = mockRegisterUseCase,
            dispatcher = testDispatcherRule.testDispatcher
        )
    }

    @Test
    fun testInitialState() {
        assertEquals(RegisterState.Idle, registerViewModel.state.value)
    }

    @Test
    fun `test onRegisterClicked sends Success state`() = runTest {
        // Given
        val user = User(
            firstname = "firstName",
            lastname = "lastName",
            email = "email",
            password = "password"
        )
        val expectedStatus = RegisterStatus.Success
        coEvery { mockRegisterUseCase(user) } returns expectedStatus

        // When
        registerViewModel.onEvent(RegisterEvent.OnRegister(
            user.firstname,
            user.lastname,
            user.email,
            user.password
        ))

        // Then
        assertEquals(RegisterState.Success, registerViewModel.state.value)
    }

    @Test
    fun `test onRegisterClicked sends Error state`() {
        // Given
        val user = User(
            firstname = "firstName",
            lastname = "lastName",
            email = "email",
            password = "password"
        )
        val exception = HttpError.INTERNET_CONNECTION
        val expectedStatus = RegisterStatus.Error(exception)
        coEvery { mockRegisterUseCase(user) } returns expectedStatus

        // When
        registerViewModel.onEvent(RegisterEvent.OnRegister(
            user.firstname,
            user.lastname,
            user.email,
            user.password
        ))

        // Then
        assertEquals(
            RegisterState.Error(exception.getErrorMessage()),
            registerViewModel.state.value
        )
        assertEquals(
            expectedStatus.httpError.getErrorMessage(),
            (registerViewModel.state.value as RegisterState.Error).message
        )
    }
}