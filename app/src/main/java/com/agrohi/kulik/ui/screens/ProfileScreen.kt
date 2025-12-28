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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit,
    navController: NavController,
    userViewModel: UserViewModel = viewModel(factory = ViewModelFactory(FirebaseAuth.getInstance()))
) {
    val uiState by userViewModel.uiState.collectAsState()

    when (val state = uiState) {
        is ProfileUiState.LoggedIn -> {
            var displayName by remember { mutableStateOf(state.user.displayName ?: "") }
            var avatar by remember { mutableStateOf(state.user.photoUrl.toString()) }
            var newPhotoUrl by remember { mutableStateOf("") }
            var isEditing by remember { mutableStateOf(false) }
            val context = LocalContext.current

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
                        Text(text = displayName)

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
                                state.user.updateProfile(profileUpdates)
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
                        .clickable {
                            userViewModel.signOut()
                            Toast
                                .makeText(
                                    context,
                                    "Logged out",
                                    Toast.LENGTH_SHORT,
                                )
                                .show()
                            navController.navigate("home")
                        }) {
                    Text("Sign out")
                }
            }
        }
        ProfileUiState.NotLoggedIn -> {
            val context = LocalContext.current
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
                        .clickable {
                            context.startActivity(Intent(context, GoogleSignInActivity::class.java))
                        }) {
                    Text("Login")
                }
            }
        }
    }
}
