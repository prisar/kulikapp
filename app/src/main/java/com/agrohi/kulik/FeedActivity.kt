package com.agrohi.kulik

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.agrohi.kulik.ui.theme.KulikTheme
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions

class FeedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KulikTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Feed()
                }
            }
        }
    }
}

data class Post(
    val id: String,
    val name: String,
    val avatar: String,
    val message: String,
    val type: String,
    val userId: String,
    val views: String,
    val likes: String
)

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Feed() {
    val db = FirebaseFirestore.getInstance()
    val posts = remember { mutableStateListOf<Post>() }

    db.collection("posts")
        .orderBy("createdAt", Query.Direction.DESCENDING)
        .limit(30)
        .get()
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    if (document.data["displayName"] != null && document.data["reported"] != true)
                        posts.add(
                            Post(
                                document.id,
                                document.data["displayName"].toString(),
                                document.data["avatar"].toString(),
                                document.data["message"].toString(),
                                document.data["type"].toString(),
                                document.data["userId"].toString(),
                                document.data["views"].toString(),
                                document.data["likes"].toString(),
                            )
                        )
                    Log.d(ContentValues.TAG, document.id + " => " + document.data)
                }
            } else {
                Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
            }
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(LightBlueBg)
    ) {
        LazyColumn() {
            itemsIndexed(posts) { index, post ->

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
                        modifier = Modifier.padding(all = 10.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp),
                        ) {
                            GlideImage(
                                model = post.avatar,
                                contentDescription = post.message,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Gray, CircleShape)
                                    .padding(1.dp)
                                    .clickable(onClick = {})
                                    .fillMaxHeight(),
                            )

                            Text(post.name, modifier = Modifier.padding(10.dp))
                        }
                        Row(
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(post.message)
                        }
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Filled.ThumbUp,
                                contentDescription = post.likes,
                                tint = Color.Blue,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable() {
                                        db
                                            .collection("posts")
                                            .document(post.id)
                                            .set(
                                                hashMapOf("likes" to post.likes + 1),
                                                SetOptions.merge()
                                            )
                                            .addOnSuccessListener {
                                                Log.d(
                                                    TAG,
                                                    "DocumentSnapshot " + post.id + " successfully written!"
                                                )
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w(
                                                    TAG,
                                                    "Error writing document",
                                                    e
                                                )
                                            }
                                    }
                            )
                            Text(post.likes)
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = post.likes,
                                tint = Color.Blue,
                                modifier = Modifier.size(28.dp)
                            )
                            Text(post.views)
                            Icon(imageVector = Icons.Filled.Warning,
                                contentDescription = post.likes,
                                tint = Color.Blue,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable() {
                                        db
                                            .collection("posts")
                                            .document(post.id)
                                            .set(hashMapOf("reported" to true), SetOptions.merge())
                                            .addOnSuccessListener {
                                                Log.d(
                                                    TAG,
                                                    "DocumentSnapshot " + post.id + " successfully written!"
                                                )
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w(
                                                    TAG,
                                                    "Error writing document",
                                                    e
                                                )
                                            }

                                        posts.drop(index)
                                    }
                            )
                            Text("Report")
                        }
                    }
                }
            }
        }
    }
}