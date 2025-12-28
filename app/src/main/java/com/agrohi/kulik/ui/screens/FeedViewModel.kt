package com.agrohi.kulik.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.agrohi.kulik.model.Post
import com.agrohi.kulik.utils.PostUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions

class FeedViewModel(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts

    fun fetchPosts() {
        db.collection("posts")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(100)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _posts.clear()
                    for (document in task.result) {
                        if (document.data["displayName"] != null && document.data["reported"] != true) {
                            val thumbnail =
                                if (document.data["type"].toString() != "video") ""
                                else PostUtils.getThumbnailUrl(
                                    document.data["userId"].toString(),
                                    document.data["video"].toString()
                                )
                            _posts.add(
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
                    }
                }
            }
    }

    fun likePost(post: Post) {
        db.collection("posts")
            .document(post.id)
            .set(
                hashMapOf(
                    "likes" to post.likes.toInt() + 1
                ),
                SetOptions.merge()
            )
            .addOnSuccessListener {
                // Ideally, we'd update the local state here or refresh
                fetchPosts()
            }
            .addOnFailureListener {
                // Do nothing
            }
    }

    fun reportPost(post: Post, index: Int) {
        db.collection("posts")
            .document(post.id)
            .set(hashMapOf("reported" to true), SetOptions.merge())
            .addOnSuccessListener {
                if (index < _posts.size) {
                    _posts.removeAt(index)
                }
            }
    }
}
