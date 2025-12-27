package com.agrohi.kulik.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Firebase

private lateinit var auth: FirebaseAuth

fun signOut() {
    Firebase.auth.signOut()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(onNavigateToHome: () -> Unit, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    var userData: String by remember { mutableStateOf("") }
    var displayName: String by remember { mutableStateOf("") }
    var avatar: String by remember { mutableStateOf("") }
    var newPhotoUrl: String by remember { mutableStateOf("") }
    var isEditing: Boolean by remember { mutableStateOf(false) }
    var context = LocalContext.current

    auth = Firebase.auth
    var currentUser = auth.getCurrentUser()
    if (currentUser == null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(LightBlueBg)
        ) {
            Text("Please login")
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(30.dp))
                    .background(Color.White)
                    .clickable() {
                        context.startActivity(Intent(context, GoogleSignInActivity::class.java))
                    }) {
                Text("Login")
            }
        }
    } else {
//        db.collection("users")
//            .document(currentUser.uid)
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    var document = task.result
//                    Log.d(TAG, document.id + " => " + document.data)
//                    avatar = document.data!!["avatar"].toString()
//                    userData = document.data!!["displayName"].toString()
//                } else {
//                    Log.w(TAG, "Error getting documents.", task.exception)
//                }
//            }
        displayName = currentUser.displayName.toString()
        avatar = currentUser.photoUrl.toString()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(LightBlueBg)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    GlideImage(
                        model = avatar,
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                            .padding(5.dp)
                            .fillMaxHeight(),
                    )
                    Text(text = userData)

                    if (isEditing) {
                        TextField(
                            value = newPhotoUrl,
                            onValueChange = { newPhotoUrl = it },
                            placeholder = { Text("Enter new photo URL") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                        Button(onClick = {
                            val profileUpdates = userProfileChangeRequest {
                                photoUri = android.net.Uri.parse(newPhotoUrl)
                            }
                            currentUser.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        avatar = newPhotoUrl
                                        isEditing = false
                                        Toast.makeText(context, "Photo updated", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }) {
                            Text("Save")
                        }
                        Button(onClick = { isEditing = false }) {
                            Text("Cancel")
                        }
                    } else {
                        Button(onClick = {
                            isEditing = true
                            newPhotoUrl = avatar
                        }) {
                            Text("Change Photo")
                        }
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(30.dp))
                    .background(Color.White)
                    .clickable() {
                        signOut()
                        Toast
                            .makeText(
                                context,
                                "Logged out",
                                Toast.LENGTH_SHORT,
                            )
                            .show()
//                        onNavigateToHome()
                        navController.navigate("home")
                    }) {
                Text("Sign out")
            }
        }
    }
}
