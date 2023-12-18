package com.alithya.profile.presentation

import com.alithya.common.domain.models.InfoUser
import com.alithya.common.domain.models.UserStatus
import com.alithya.common.domain.usecases.UseCasesCommon
import com.alithya.common.domain.usecases.get_me_information.GetMeUseCase
import com.alithya.common.utils.HttpError
import com.alithya.profile.TestDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val getMeUseCase: GetMeUseCase = mockk()
    private val useCaseCommonUseCaseMock : UseCasesCommon = mockk()
    private lateinit var profileViewModel: ProfileViewModel

    @Before
    fun setup() {
        profileViewModel = ProfileViewModel(
            getMeUseCase = getMeUseCase,
            useCasesCommon = useCaseCommonUseCaseMock,
            dispatcher = testDispatcherRule.testDispatcher
        )
    }

    @Test
    fun testInitialState() = runTest {
        assertEquals(ProfileState.Idle, profileViewModel.state.value)
    }

    @Test
    fun `test received UserStatus success`() = runTest {
        // Given
        val user = InfoUser(
            id = "id",
            firstName = "firstname",
            lastName = "lastname",
            email = "email",
            name = "name",
            managerFullName = "managerFullName",
            jobTitle = "jobTitle",
            imageUrl = "imageUrl",
            displayName = "displayName",
        )
        val expectedResponse = flowOf(UserStatus.Success(user))

        coEvery { useCaseCommonUseCaseMock.saveCurrentUserIdUseCase(any()) } returns Unit
        coEvery { getMeUseCase() } returns expectedResponse

        // When
        profileViewModel.onEvent(ProfileEvent.OnLoadMyProfile)

        // Then
        assertEquals(ProfileState.Success(user), profileViewModel.state.value)
        assertEquals(
            UserStatus.Success(user).data,
            (profileViewModel.state.value as ProfileState.Success).user
        )
    }

    @Test
    fun `test received UserStatus failure`() = runTest {
        // Given
        val exception = HttpError.UNKNOWN
        val expectedResponse = flowOf(UserStatus.Error(exception))

        coEvery { getMeUseCase.invoke() } returns expectedResponse

        // When
        profileViewModel.onEvent(ProfileEvent.OnLoadMyProfile)

        // Then
        assertEquals(ProfileState.Error(exception.getErrorMessage()), profileViewModel.state.value)
        assertEquals(
            ProfileState.Error(exception.getErrorMessage()).message,
            (profileViewModel.state.value as ProfileState.Error).message
        )
    }
}