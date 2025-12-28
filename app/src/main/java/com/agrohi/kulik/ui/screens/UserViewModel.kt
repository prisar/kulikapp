package com.agrohi.kulik.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ProfileUiState {
    object NotLoggedIn : ProfileUiState
    data class LoggedIn(val user: FirebaseUser) : ProfileUiState
}

class UserViewModel(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.NotLoggedIn)
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        viewModelScope.launch {
            auth.addAuthStateListener {
                val user = it.currentUser
                if (user != null) {
                    _uiState.value = ProfileUiState.LoggedIn(user)
                } else {
                    _uiState.value = ProfileUiState.NotLoggedIn
                }
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }
}