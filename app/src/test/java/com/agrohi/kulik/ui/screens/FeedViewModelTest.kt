package com.agrohi.kulik.ui.screens

import com.agrohi.kulik.model.Post
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FeedViewModelTest {

    private lateinit var viewModel: FeedViewModel
    private val db = mockk<FirebaseFirestore>(relaxed = true)

    @Before
    fun setUp() {
        viewModel = FeedViewModel(db)
    }

    @Test
    fun `fetchPosts calls firestore to get posts`() {
        val collectionRef = mockk<CollectionReference>(relaxed = true)
        val query = mockk<Query>(relaxed = true)
        
        every { db.collection("posts") } returns collectionRef
        every { collectionRef.orderBy("createdAt", Query.Direction.DESCENDING) } returns query
        every { query.limit(100) } returns query
        
        viewModel.fetchPosts()
        
        verify { collectionRef.orderBy("createdAt", Query.Direction.DESCENDING) }
        verify { query.limit(100) }
        verify { query.get() }
    }

    @Test
    fun `likePost updates likes count in firestore`() {
        val post = Post("id1", "Name", "Avatar", "Message", "type", "userId", "10", "5", "photoUrl", "video", "thumbnail")
        val docRef = mockk<DocumentReference>(relaxed = true)
        
        every { db.collection("posts").document(post.id) } returns docRef
        
        viewModel.likePost(post)
        
        verify { docRef.set(match<HashMap<String, Any>> { it["likes"] == 6 }, any()) }
    }

    @Test
    fun `reportPost updates reported status in firestore`() {
        val post = Post("id1", "Name", "Avatar", "Message", "type", "userId", "10", "5", "photoUrl", "video", "thumbnail")
        val docRef = mockk<DocumentReference>(relaxed = true)
        
        every { db.collection("posts").document(post.id) } returns docRef
        
        viewModel.reportPost(post, 0)
        
        verify { docRef.set(match<HashMap<String, Any>> { it["reported"] == true }, any()) }
    }
}
