package com.agrohi.kulik.ui.screens

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertNotNull
import org.junit.Test

class UserViewModelTest {

    @Test
    fun userViewModel_initialization_withUserId_isSuccessful() {
        val savedStateHandle = SavedStateHandle(mapOf("userId" to "test_user_id"))
        val viewModel = UserViewModel(savedStateHandle)
        
        // Since userId is private and no other public state is available yet,
        // we just verify initialization doesn't throw.
        // In a real scenario, we would verify that userInfo or other fields are updated.
        assertNotNull(viewModel)
    }

    @Test(expected = IllegalStateException::class)
    fun userViewModel_initialization_withoutUserId_throwsException() {
        val savedStateHandle = SavedStateHandle()
        UserViewModel(savedStateHandle)
    }
}
