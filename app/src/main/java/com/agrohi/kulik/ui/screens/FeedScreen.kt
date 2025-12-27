package com.agrohi.kulik.ui.screens

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agrohi.kulik.model.Post
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.agrohi.kulik.ui.theme.PinkBg
import com.agrohi.kulik.ui.theme.Red249
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeedScreen(
    navController: NavController,
) {
    val db = FirebaseFirestore.getInstance()
    val posts = remember { mutableStateListOf<Post>() }
    val context = LocalContext.current

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "You are reporting this post")
            },
            text = {
                Text(text = "Are you sure you want to report this post?")
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(100.dp)
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(Red249)
                            .clickable() { openDialog.value = false }
                    ) {
                        Text("Submit")
                    }
                }
            },
            dismissButton = {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(100.dp)
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(Color.LightGray)
                            .clickable() { openDialog.value = false }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        )
    }

    db.collection("posts")
        .orderBy("createdAt", Query.Direction.DESCENDING)
        .limit(100)
        .get()
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    if (document.data["displayName"] != null && document.data["reported"] != true) {
                        val thumbnail =
                            if (document.data["type"].toString() != "video") "" else "https://firebasestorage.googleapis.com/v0/b/agrohikulik.appspot.com/o/images%2Fposts%2F" + "videos/${document.data["userId"]}/thumbnails/${document.data["video"]}.png"
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
                                document.data["photoUrl"].toString(),
                                document.data["video"].toString(),
                                thumbnail,
                            )
                        )
                    }
//                    Log.d(ContentValues.TAG, document.id + " => " + document.data)
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
                    shape = RoundedCornerShape(5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
//                        .padding(bottom = 3.dp)
                        .height(450.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(all = 1.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .height(40.dp),
                        ) {
                            GlideImage(
                                model = post.avatar,
                                contentDescription = post.message,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Gray, CircleShape)
                                    .padding(1.dp)
                                    .clickable(onClick = {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                UserActivity::class.java
                                            ).putExtra("userId", post.userId)
                                        )
                                    })
                                    .fillMaxHeight(),
                            )

                            Text(post.name, modifier = Modifier.padding(10.dp))
                        }
                        Row(
                            modifier = Modifier
                                .height(350.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (post.thumbnail.isNotEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .height(350.dp)
                                        .fillMaxWidth(),
                                ) {
                                    GlideImage(
                                        model = post.thumbnail,
                                        contentDescription = post.message,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                    )
                                }
                            } else {
                                if (post.photoUrl.isNotEmpty() && post.photoUrl != "null") {
                                    Row(
                                        modifier = Modifier
                                            .height(350.dp)
                                            .fillMaxWidth(),
                                    ) {
                                        GlideImage(
                                            model = post.photoUrl,
                                            contentDescription = post.message,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight(),
                                        )
                                    }
                                } else {
                                    Row(
                                        modifier = Modifier
                                            .height(350.dp)
                                            .fillMaxWidth()
                                            .clip(shape = RoundedCornerShape(5.dp))
                                            .background(PinkBg),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            post.message,
                                            style = TextStyle(
                                                fontSize = 28.sp,
                                                fontWeight = FontWeight.W700
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .height(55.dp)
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Filled.ThumbUp,
                                contentDescription = post.likes,
                                tint = Red249,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable() {
                                        db
                                            .collection("posts")
                                            .document(post.id)
                                            .set(
                                                hashMapOf(
                                                    "likes" to post.likes
                                                        .toString()
                                                        .toInt() + 1
                                                ),
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
                            Text(
                                post.likes, fontWeight = FontWeight.W300, modifier = Modifier
                                    .padding(5.dp)
                            )
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = post.likes,
                                tint = Color.Black,
                                modifier = Modifier.size(28.dp)
                            )
                            Text(
                                post.views, fontWeight = FontWeight.W300, modifier = Modifier
                                    .padding(5.dp)
                            )
                            Icon(imageVector = Icons.Filled.Warning,
                                contentDescription = post.likes,
                                tint = Color.LightGray,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable() {
                                        openDialog.value = true

                                        db
                                            .collection("posts")
                                            .document(post.id)
                                            .set(hashMapOf("reported" to true), SetOptions.merge())
                                            .addOnSuccessListener {
//                                                Toast.makeText(context, "Post is reported successfully", Toast.LENGTH_SHORT).show()
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
//                            Text("Report")
                        }
                    }
                }
            }
        }
    }
}