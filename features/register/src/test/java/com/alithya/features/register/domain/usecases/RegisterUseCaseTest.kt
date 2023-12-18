package com.alithya.features.register.domain.usecases

import com.alithya.common.utils.HttpError
import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User
import com.alithya.features.register.domain.repository.RegisterRepository
import com.alithya.features.register.domain.usecases.RegisterUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class RegisterUseCaseTest {

    private val registerRepository : RegisterRepository = mockk()
    private val registerUseCase = RegisterUseCase(registerRepository)

    @Test
    fun `invoke should return Success when register is successful`() = runTest {
        // given
        val user = User(
            firstname = "firstName",
            lastname = "lastName",
            email = "firstName.lastName@example.com",
            password = "password",
        )
        val registerStatus = RegisterStatus.Success
        coEvery { registerRepository.register(user) } returns registerStatus

        // when
        val actualStatus = registerUseCase(user)

        // then
        coVerify { registerRepository.register(user) }
        assertEquals(registerStatus, actualStatus)
    }

    @Test
    fun `invoke should return Error when register is not successful`() = runTest {
        // given
        val user = User(
            firstname = "firstName",
            lastname = "lastName",
            email = "firstName.lastName@example.com",
            password = "password",
        )
        val exception = HttpError.INTERNET_CONNECTION
        val registerStatus = RegisterStatus.Error(exception)
        coEvery { registerRepository.register(user) } returns RegisterStatus.Error(exception)

        // when
        val actualStatus = registerUseCase(user)

        // then
        coVerify { registerRepository.register(user) }
        assertEquals(registerStatus, actualStatus)

        coVerify(exactly = 1) { registerRepository.register(user) }
        assertEquals(registerStatus, actualStatus)
    }
}