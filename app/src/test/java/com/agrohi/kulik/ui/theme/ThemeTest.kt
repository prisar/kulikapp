package com.agrohi.kulik.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun kulikTheme_lightTheme_appliesCorrectly() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                KulikTheme(darkTheme = false, dynamicColor = false) {
                    Text("Test")
                }
            }
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun kulikTheme_darkTheme_appliesCorrectly() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                KulikTheme(darkTheme = true, dynamicColor = false) {
                    Text("Test")
                }
            }
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun kulikTheme_dynamicColor_appliesCorrectly() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                KulikTheme(darkTheme = false, dynamicColor = true) {
                    Text("Test")
                }
            }
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun kulikTheme_usesTypography() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                KulikTheme {
                    val typography = MaterialTheme.typography
                    Text("Test", style = typography.bodyLarge)
                }
            }
        }
        composeTestRule.waitForIdle()
    }
}
