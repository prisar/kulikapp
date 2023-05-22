package com.agrohi.kulik

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agrohi.kulik.ui.theme.KulikTheme
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private lateinit var auth: FirebaseAuth
class AddPostActivity : ComponentActivity() {

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
                    AddPostScreen()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = auth.getCurrentUser()
        if (currentUser != null) {
//            userData = currentUser
        }
    }



    companion object {
        const val TAG = "AddPostActivity"
    }
}
@Composable
fun AddPostScreen() {
    var message by remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(LightBlueBg)
    ) {

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 1.dp,
            backgroundColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .height(250.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.background(LightBlueBg)
            ) {
                Row() {
                    Text("Add post")
                }

                Row() {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth().height(120.dp)
                            .padding(start = 15.dp, top = 10.dp, end = 15.dp)
                            .background(Color.White, RoundedCornerShape(5.dp)),
                        shape = RoundedCornerShape(5.dp),
                        value = message,
                        onValueChange = { message = it },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        maxLines = 3,
                        label = { Text("Write the description of your post") },
                    )
                }

                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(70.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(30.dp))
                        .background(Color.White)
                        .clickable() {
                            val currentUser = auth.getCurrentUser()
                            val createdAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ").format( Date())
                            if (currentUser != null) {
                                val data = hashMapOf(
                                    "avatar" to currentUser.photoUrl,
                                    "createdAt" to createdAt,
                                    "displayName" to currentUser.displayName,
                                    "likes" to 0,
                                    "message" to message,
                                    "shares" to 0,
                                    "type" to "photo",
                                    "userId" to currentUser.uid,
                                    "views" to 0,
                                )

                                db
                                    .collection("posts")
                                    .add(data)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(AddPostActivity.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(AddPostActivity.TAG, "Error adding document", e)
                                    }
                            }
                        }
                ) {
                    Text("Create",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}