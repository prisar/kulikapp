package com.agrohi.kulik

import com.agrohi.kulik.model.Post
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testPostCreation() {
        val post = Post(
            id = "1",
            name = "John Doe",
            avatar = "avatar_url",
            message = "This is a test post.",
            type = "text",
            userId = "user1",
            views = "100",
            likes = "10",
            photoUrl = "photo_url",
            video = "video_url",
            thumbnail = "thumbnail_url"
        )

        assertEquals("1", post.id)
        assertEquals("John Doe", post.name)
        assertEquals("avatar_url", post.avatar)
        assertEquals("This is a test post.", post.message)
        assertEquals("text", post.type)
        assertEquals("user1", post.userId)
        assertEquals("100", post.views)
        assertEquals("10", post.likes)
        assertEquals("photo_url", post.photoUrl)
        assertEquals("video_url", post.video)
        assertEquals("thumbnail_url", post.thumbnail)
    }
}