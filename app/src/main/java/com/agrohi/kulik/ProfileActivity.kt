package com.agrohi.kulik

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.agrohi.kulik.ui.theme.KulikTheme
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.google.firebase.firestore.FirebaseFirestore


class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KulikTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Profile()
                }
            }
        }
    }
}

@Composable
fun Profile() {
    val db = FirebaseFirestore.getInstance()
    var userData: String  by remember { mutableStateOf("") }

//    db.collection("users")
//        .document("VTlIMNIsGRNiDLMb5lGKZ1Wgg5G3")
//        .get()
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                var document = task.result
//                Log.d(TAG, document.id + " => " + document.data)
//                userData = document.data.toString()
//            } else {
//                Log.w(TAG, "Error getting documents.", task.exception)
//            }
//        }

    Text(text = userData)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(LightBlueBg)
    ) {
        Text("null")
    }
}
