package com.agrohi.kulik.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorTest {

    @Test
    fun purple80_hasCorrectValue() {
        assertEquals(Color(0xFFD0BCFF), Purple80)
    }

    @Test
    fun purpleGrey80_hasCorrectValue() {
        assertEquals(Color(0xFFCCC2DC), PurpleGrey80)
    }

    @Test
    fun pink80_hasCorrectValue() {
        assertEquals(Color(0xFFEFB8C8), Pink80)
    }

    @Test
    fun purple40_hasCorrectValue() {
        assertEquals(Color(0xFF6650a4), Purple40)
    }

    @Test
    fun purpleGrey40_hasCorrectValue() {
        assertEquals(Color(0xFF625b71), PurpleGrey40)
    }

    @Test
    fun pink40_hasCorrectValue() {
        assertEquals(Color(0xFF7D5260), Pink40)
    }

    @Test
    fun lightGreen_hasCorrectValue() {
        assertEquals(Color(0xFF8BC34A), LightGreen)
    }

    @Test
    fun lightBlueBg_hasCorrectValue() {
        assertEquals(Color(0xFFE3F2FD), LightBlueBg)
    }

    @Test
    fun exploreCardBlue_hasCorrectValue() {
        assertEquals(Color(0xFF42A5F5), exploreCardBlue)
    }

    @Test
    fun exploreCardYellow_hasCorrectValue() {
        assertEquals(Color(0xFFFFEE58), exploreCardYellow)
    }

    @Test
    fun pinkBg_hasCorrectValue() {
        assertEquals(Color(0xFFFCE4EC), PinkBg)
    }

    @Test
    fun red249_hasCorrectValue() {
        assertEquals(Color(0xFFF9BDBD), Red249)
    }
}
