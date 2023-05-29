package com.agrohi.kulik.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import com.agrohi.kulik.ui.theme.KulikTheme
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            KulikTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Profile( {}, navController)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = auth.getCurrentUser()
        if (currentUser == null) {
            baseContext.startActivity(Intent(baseContext, EmailPasswordActivity::class.java))
        }
    }
}

fun signOut() {
    Firebase.auth.signOut()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Profile( onNavigateToHome: () -> Unit, navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    var userData: String by remember { mutableStateOf("") }
    var displayName: String by remember { mutableStateOf("") }
    var avatar: String by remember { mutableStateOf("") }
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
                    .height(200.dp)
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
                        onNavigateToHome()
//                        navController.navigate("explore") {
//                            popUpTo("profile")
//                        }
                    }) {
                Text("Sign out")
            }
        }
    }
}
