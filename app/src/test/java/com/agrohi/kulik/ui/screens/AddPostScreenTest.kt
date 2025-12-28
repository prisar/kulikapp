package com.agrohi.kulik.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.agrohi.kulik.MainDispatcherRule
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class AddPostScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    private val navController = mockk<NavController>(relaxed = true)
    private val viewModel = mockk<AddPostViewModel>(relaxed = true)

    @Test
    fun addPostScreen_createButton_isDisplayed_and_clickable() {
        composeTestRule.setContent {
            AddPostScreen(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Create").assertExists()
        composeTestRule.onNodeWithText("Create").performClick()

        verify {
            viewModel.createPost(
                any(),
                any(),
                any()
            )
        }
    }
}
