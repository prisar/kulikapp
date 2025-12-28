package com.agrohi.kulik.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import com.agrohi.kulik.MainDispatcherRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    private val navController = mockk<NavController>(relaxed = true)

    @Test
    fun homeScreen_historyTitle_isDisplayed() {
        composeTestRule.setContent {
            HomeScreen(navController = navController)
        }

        composeTestRule.onNodeWithText("History").assertExists()
    }
}
