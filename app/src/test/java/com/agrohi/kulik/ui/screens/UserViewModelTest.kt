package com.agrohi.kulik.ui.screens

import com.agrohi.kulik.MainDispatcherRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    private lateinit var viewModel: UserViewModel
    private val auth: FirebaseAuth = mockk(relaxed = true)
    private val user: FirebaseUser = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = UserViewModel(auth)
    }

    @Test
    fun `when user is logged in, uiState is LoggedIn`() = runTest {
        // Given
        every { auth.currentUser } returns user
        val listener = slot<FirebaseAuth.AuthStateListener>()
        every { auth.addAuthStateListener(capture(listener)) } answers {
            listener.captured.onAuthStateChanged(auth)
        }

        // When
        viewModel = UserViewModel(auth)

        // Then
        val uiState = viewModel.uiState.first()
        assertEquals(ProfileUiState.LoggedIn(user), uiState)
    }

    @Test
    fun `when user is not logged in, uiState is NotLoggedIn`() = runTest {
        // Given
        every { auth.currentUser } returns null
        val listener = slot<FirebaseAuth.AuthStateListener>()
        every { auth.addAuthStateListener(capture(listener)) } answers {
            listener.captured.onAuthStateChanged(auth)
        }

        // When
        viewModel = UserViewModel(auth)

        // Then
        val uiState = viewModel.uiState.first()
        assertEquals(ProfileUiState.NotLoggedIn, uiState)
    }

    @Test
    fun `signOut calls auth signOut`() {
        // When
        viewModel.signOut()

        // Then
        verify { auth.signOut() }
    }
}
