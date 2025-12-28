package com.agrohi.kulik.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import com.agrohi.kulik.MainDispatcherRule
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    private val navController = mockk<NavController>(relaxed = true)
    private val userViewModel = mockk<UserViewModel>(relaxed = true)
    private val user = mockk<FirebaseUser>(relaxed = true)

    @Test
    fun profileScreen_notLoggedIn_showsLoginButton() {
        every { userViewModel.uiState } returns MutableStateFlow(ProfileUiState.NotLoggedIn)

        composeTestRule.setContent {
            ProfileScreen(
                onNavigateToHome = {},
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composeTestRule.onNodeWithText("Login").assertExists()
    }

    @Test
    fun profileScreen_loggedIn_showsSignOutButton() {
        every { user.displayName } returns "Test User"
        every { user.photoUrl } returns mockk(relaxed = true)
        every { userViewModel.uiState } returns MutableStateFlow(ProfileUiState.LoggedIn(user))

        composeTestRule.setContent {
            ProfileScreen(
                onNavigateToHome = {},
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composeTestRule.onNodeWithText("Sign out").assertExists()
    }
}