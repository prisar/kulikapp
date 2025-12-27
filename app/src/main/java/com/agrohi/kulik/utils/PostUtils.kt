package com.agrohi.kulik.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PostUtils {
    fun getThumbnailUrl(userId: String, video: String): String {
        return "https://firebasestorage.googleapis.com/v0/b/agrohikulik.appspot.com/o/images%2Fposts%2Fvideos/$userId/thumbnails/$video.png"
    }

    fun getCurrentDateTime(): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault()).format(Date())
    }
}
