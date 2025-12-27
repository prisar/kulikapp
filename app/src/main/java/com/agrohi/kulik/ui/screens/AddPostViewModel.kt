package com.agrohi.kulik.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.agrohi.kulik.utils.PostUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddPostViewModel(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {
    var message by mutableStateOf("")

    fun updateMessage(newMessage: String) {
        message = newMessage
    }

    fun createPost(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        onNotSignedIn: () -> Unit
    ) {
        val currentUser = auth.currentUser
        val createdAt = PostUtils.getCurrentDateTime()
        
        if (currentUser != null) {
            val data = hashMapOf(
                "avatar" to currentUser.photoUrl.toString(),
                "createdAt" to createdAt,
                "displayName" to currentUser.displayName,
                "likes" to 0,
                "message" to message,
                "shares" to 0,
                "type" to "photo",
                "userId" to currentUser.uid,
                "views" to 0,
            )

            db.collection("posts")
                .add(data)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
        } else {
            onNotSignedIn()
        }
    }
}
