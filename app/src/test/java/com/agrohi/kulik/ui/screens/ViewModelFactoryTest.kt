package com.agrohi.kulik.ui.screens

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ViewModelFactoryTest {
    private lateinit var auth: FirebaseAuth
    private lateinit var factory: ViewModelFactory

    @Before
    fun setup() {
        auth = mockk(relaxed = true)
        factory = ViewModelFactory(auth)
    }

    @Test
    fun create_userViewModel_success() {
        val viewModel = factory.create(UserViewModel::class.java)
        assertNotNull(viewModel)
        assertTrue(viewModel is UserViewModel)
    }

    @Test
    fun create_unknownViewModel_throwsException() {
        class UnknownViewModel : ViewModel()

        assertThrows(IllegalArgumentException::class.java) {
            factory.create(UnknownViewModel::class.java)
        }
    }
}
