package com.agrohi.kulik.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PostTest {

    @Test
    fun postCreation_setsPropertiesCorrectly() {
        val post = Post(
            id = "1",
            name = "Test User",
            avatar = "avatar_url",
            message = "Hello World",
            type = "photo",
            userId = "user1",
            views = "10",
            likes = "5",
            photoUrl = "photo_url",
            video = "",
            thumbnail = ""
        )

        assertEquals("1", post.id)
        assertEquals("Test User", post.name)
        assertEquals("avatar_url", post.avatar)
        assertEquals("Hello World", post.message)
        assertEquals("photo", post.type)
        assertEquals("user1", post.userId)
        assertEquals("10", post.views)
        assertEquals("5", post.likes)
        assertEquals("photo_url", post.photoUrl)
        assertEquals("", post.video)
        assertEquals("", post.thumbnail)
    }
}
