package com.agrohi.kulik.ui.screens

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agrohi.kulik.ui.theme.KulikTheme
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val userId = intent.getStringExtra("userId").toString()

        setContent {
            KulikTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserDetails(userId)
                }
            }
        }
    }
}

data class User(
    val userId: String = "",
    var displayName: String = "",
    var avatar: String = "",
    val reported: Boolean = false
)

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserDetails(userId: String) {
    var displayName by remember { mutableStateOf("") }
    var avatar by remember {
        mutableStateOf("")
    }
    var reported by remember { mutableStateOf(false) }
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    db.collection("users").document(userId)
        .get()
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val userData = task.result
                displayName = userData?.data?.get("displayName").toString()
                avatar = userData?.data?.get("avatar").toString()
                reported = userData?.data?.get("reported").toString().toBoolean()
                Log.w(ContentValues.TAG, "user " + displayName + " " + userData.data.toString())
            } else {
                Log.w(ContentValues.TAG, "Error getting document.", task.exception)
            }
        }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(LightBlueBg)
    ) {

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .height(350.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                Row(verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()) {
                    Text("Press the icon to report the user", modifier = Modifier.padding(15.dp))

                    Icon(Icons.Filled.Warning, contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(10.dp)
                            .clickable() {
                                db
                                    .collection("users")
                                    .document(userId)
                                    .set(hashMapOf("reported" to true), SetOptions.merge())
                                    .addOnSuccessListener {
                                        Toast
                                            .makeText(
                                                context,
                                                "User is reported successfully.",
                                                Toast.LENGTH_SHORT,
                                            )
                                            .show()
                                        Log.d(
                                            ContentValues.TAG,
                                            "DocumentSnapshot $userId successfully written!"
                                        )
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(
                                            ContentValues.TAG,
                                            "Error writing document",
                                            e
                                        )
                                    }
                            })
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .height(100.dp)
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    GlideImage(
                        model = avatar,
                        contentDescription = displayName,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                            .padding(1.dp)
                            .clickable(onClick = {

                            })
                            .fillMaxHeight(),
                    )
                }

                Row(verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(displayName, style = TextStyle(fontWeight = FontWeight.W700, fontSize = 32.sp), modifier = Modifier.padding(10.dp))
                }

                if (reported)  {
                    Row(verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .height(30.dp)
                            .padding(5.dp)
                            .fillMaxWidth()) {
                        Text(text = "The user has already been reported",
                            style = TextStyle(color = Color.Red),
                        )
                    }
                }
            }
        }
    }
}