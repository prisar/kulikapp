package com.agrohi.kulik

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import com.agrohi.kulik.ui.theme.KulikTheme
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
//        setContent {
//            KulikTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Profile()
//                }
//            }
//        }
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

@Composable
fun Profile() {
    val db = FirebaseFirestore.getInstance()
    var userData: String  by remember { mutableStateOf("") }
    var context = LocalContext.current

    auth = Firebase.auth
    var currentUser = auth.getCurrentUser()
    if (currentUser == null) {
        context.startActivity(Intent(context, GoogleSignInActivity::class.java))
    } else {
        db.collection("users")
            .document(currentUser.uid)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var document = task.result
                    Log.d(TAG, document.id + " => " + document.data)
                    userData = document.data.toString()
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(LightBlueBg)
    ) {
        Text(text = userData)

        Row(modifier = Modifier.clickable() {
            signOut()
        }) {
            Text("Sign out")
        }
    }
}
