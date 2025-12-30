package com.agrohi.kulik.ui.screens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class UserActivityTest {

    @Test
    fun userActivity_classExists() {
        val activityClass = UserActivity::class.java
        assert(activityClass != null)
    }

    @Test
    fun user_dataClass_defaultValues() {
        val user = User()

        assertEquals("", user.userId)
        assertEquals("", user.displayName)
        assertEquals("", user.avatar)
        assertFalse(user.reported)
    }

    @Test
    fun user_dataClass_withCustomValues() {
        val user = User(
            userId = "user123",
            displayName = "Test User",
            avatar = "https://example.com/avatar.jpg",
            reported = true
        )

        assertEquals("user123", user.userId)
        assertEquals("Test User", user.displayName)
        assertEquals("https://example.com/avatar.jpg", user.avatar)
        assertEquals(true, user.reported)
    }
}
