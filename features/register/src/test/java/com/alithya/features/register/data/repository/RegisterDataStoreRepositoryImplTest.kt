package com.alithya.features.register.data.repository

import com.alithya.common.utils.HttpError
import com.alithya.features.register.data.repository.RegisterRepositoryImpl
import com.alithya.features.register.data.repository.datasource.RegisterRemoteDataSource
import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterDataStoreRepositoryImplTest {

    private val registerRemoteDataSource = mockk<RegisterRemoteDataSource>()
    private val registerRepository = RegisterRepositoryImpl(registerRemoteDataSource)

    @Test
    fun `register() returns success`() = runTest {
        // given
        val user = User(
            firstname = "firstName",
            lastname = "lastName",
            email = "firstName.lastName@example.com",
            password = "password",
        )
        val expectedStatus = RegisterStatus.Success
        coEvery { registerRemoteDataSource.register(user) } returns expectedStatus

        // when
        val actualStatus = registerRepository.register(user)

        // then
        assertEquals(expectedStatus, actualStatus)
    }

    @Test
    fun `register() returns error`() = runTest {
        // given
        val user = User(
            firstname = "firstName",
            lastname = "lastName",
            email = "firstName.lastName@example.com",
            password = "password",
        )
        val expectedStatus = RegisterStatus.Error(HttpError.INTERNET_CONNECTION)
        coEvery { registerRemoteDataSource.register(user) } returns expectedStatus

        // when
        val actualStatus = registerRepository.register(user)

        // then
        assertEquals(expectedStatus, actualStatus)
    }
}
