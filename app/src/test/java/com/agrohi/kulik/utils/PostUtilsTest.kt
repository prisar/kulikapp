package com.agrohi.kulik.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.regex.Pattern

class PostUtilsTest {

    @Test
    fun getThumbnailUrl_returnsCorrectUrl() {
        val userId = "user123"
        val videoId = "video456"
        val expected = "https://firebasestorage.googleapis.com/v0/b/agrohikulik.appspot.com/o/images%2Fposts%2Fvideos/user123/thumbnails/video456.png"
        val result = PostUtils.getThumbnailUrl(userId, videoId)
        assertEquals(expected, result)
    }

    @Test
    fun getCurrentDateTime_returnsFormattedString() {
        val result = PostUtils.getCurrentDateTime()
        // Format: yyyy-MM-dd'T'HH:mm:ssZZZZZ
        // Example: 2023-10-27T10:00:00+05:30
        val regex = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]\\d{2}:?\\d{2}$"
        assertTrue("Date format should match ISO 8601, got: $result", 
            Pattern.compile(regex).matcher(result).matches())
    }
}
