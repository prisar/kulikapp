package com.agrohi.kulik.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeTest {

    @Test
    fun typography_bodyLarge_hasCorrectFontFamily() {
        assertEquals(FontFamily.Default, Typography.bodyLarge.fontFamily)
    }

    @Test
    fun typography_bodyLarge_hasCorrectFontWeight() {
        assertEquals(FontWeight.Normal, Typography.bodyLarge.fontWeight)
    }

    @Test
    fun typography_bodyLarge_hasCorrectFontSize() {
        assertEquals(16.sp, Typography.bodyLarge.fontSize)
    }

    @Test
    fun typography_bodyLarge_hasCorrectLineHeight() {
        assertEquals(24.sp, Typography.bodyLarge.lineHeight)
    }

    @Test
    fun typography_bodyLarge_hasCorrectLetterSpacing() {
        assertEquals(0.5.sp, Typography.bodyLarge.letterSpacing)
    }
}
