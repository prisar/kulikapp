package com.agrohi.kulik.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import com.agrohi.kulik.R
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class AppNavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screen_profile_hasCorrectRoute() {
        assertEquals("profile", Screen.Profile.route)
    }

    @Test
    fun screen_profile_hasCorrectResourceId() {
        assertEquals(R.string.profile, Screen.Profile.resourceId)
    }

    @Test
    fun screen_home_hasCorrectRoute() {
        assertEquals("home", Screen.Home.route)
    }

    @Test
    fun screen_home_hasCorrectResourceId() {
        assertEquals(R.string.main, Screen.Home.resourceId)
    }

    @Test
    fun screen_explore_hasCorrectRoute() {
        assertEquals("explore", Screen.Explore.route)
    }

    @Test
    fun screen_explore_hasCorrectResourceId() {
        assertEquals(R.string.explore, Screen.Explore.resourceId)
    }

    @Test
    fun screen_addPost_hasCorrectRoute() {
        assertEquals("addpost", Screen.AddPost.route)
    }

    @Test
    fun screen_addPost_hasCorrectResourceId() {
        assertEquals(R.string.post, Screen.AddPost.resourceId)
    }

    @Test
    fun screen_feed_hasCorrectRoute() {
        assertEquals("feed", Screen.Feed.route)
    }

    @Test
    fun screen_feed_hasCorrectResourceId() {
        assertEquals(R.string.feed, Screen.Feed.resourceId)
    }

    @Test
    fun screen_googleSignIn_hasCorrectRoute() {
        assertEquals("googlesignin", Screen.GoogleSignIn.route)
    }

    @Test
    fun destinations_basicsStart_hasCorrectValue() {
        assertEquals("Main", Destinations.BASICS_START)
    }

    @Test
    fun appNavGraph_composable_renders() {
        composeTestRule.setContent {
            AppNavGraph()
        }
        composeTestRule.waitForIdle()
    }
}
