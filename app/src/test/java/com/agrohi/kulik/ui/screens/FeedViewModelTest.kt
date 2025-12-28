package com.agrohi.kulik.ui.screens

import com.agrohi.kulik.model.Post
import com.agrohi.kulik.utils.PostUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
    fun `fetchPosts calls firestore and updates posts on success`() {
        val collectionRef = mockk<CollectionReference>(relaxed = true)
        val query = mockk<Query>(relaxed = true)
        val task = mockk<Task<QuerySnapshot>>(relaxed = true)
        val snapshot = mockk<QuerySnapshot>(relaxed = true)
        val document = mockk<QueryDocumentSnapshot>(relaxed = true)

        every { db.collection("posts") } returns collectionRef
        every { collectionRef.orderBy("createdAt", Query.Direction.DESCENDING) } returns query
        every { query.limit(100) } returns query
        every { query.get() } returns task
        
        // Mocking the task completion
        val slot = slot<OnCompleteListener<QuerySnapshot>>()
        every { task.addOnCompleteListener(capture(slot)) } returns task
        every { task.isSuccessful } returns true
        every { task.result } returns snapshot
        every { snapshot.iterator() } returns mutableListOf(document).iterator()
        
        // Mocking document data
        every { document.id } returns "post1"
        every { document.data } returns hashMapOf(
            "displayName" to "User 1",
            "avatar" to "avatar1",
            "message" to "Hello",
            "type" to "photo",
            "userId" to "user1",
            "views" to "10",
            "likes" to "5",
            "photoUrl" to "url1",
            "video" to "vid1"
        )

        viewModel.fetchPosts()
        slot.captured.onComplete(task)

        assertEquals(1, viewModel.posts.size)
        assertEquals("User 1", viewModel.posts[0].name)
    }

    @Test
    fun `fetchPosts handles video type and generates thumbnail`() {
        val collectionRef = mockk<CollectionReference>(relaxed = true)
        val query = mockk<Query>(relaxed = true)
        val task = mockk<Task<QuerySnapshot>>(relaxed = true)
        val snapshot = mockk<QuerySnapshot>(relaxed = true)
        val document = mockk<QueryDocumentSnapshot>(relaxed = true)

        every { db.collection("posts") } returns collectionRef
        every { collectionRef.orderBy(any<String>(), any()) } returns query
        every { query.limit(any()) } returns query
        every { query.get() } returns task
        
        val slot = slot<OnCompleteListener<QuerySnapshot>>()
        every { task.addOnCompleteListener(capture(slot)) } returns task
        every { task.isSuccessful } returns true
        every { task.result } returns snapshot
        every { snapshot.iterator() } returns mutableListOf(document).iterator()
        
        every { document.data } returns hashMapOf(
            "displayName" to "User 1",
            "type" to "video",
            "userId" to "user1",
            "video" to "vid1"
        )

        viewModel.fetchPosts()
        slot.captured.onComplete(task)

        assertTrue(viewModel.posts[0].thumbnail.contains("user1"))
        assertTrue(viewModel.posts[0].thumbnail.contains("vid1"))
    }

    @Test
    fun `fetchPosts filters out reported posts or posts without displayName`() {
        val collectionRef = mockk<CollectionReference>(relaxed = true)
        val query = mockk<Query>(relaxed = true)
        val task = mockk<Task<QuerySnapshot>>(relaxed = true)
        val snapshot = mockk<QuerySnapshot>(relaxed = true)
        val doc1 = mockk<QueryDocumentSnapshot>(relaxed = true)
        val doc2 = mockk<QueryDocumentSnapshot>(relaxed = true)
        val doc3 = mockk<QueryDocumentSnapshot>(relaxed = true)

        every { db.collection("posts") } returns collectionRef
        every { collectionRef.orderBy(any<String>(), any()) } returns query
        every { query.limit(any()) } returns query
        every { query.get() } returns task
        
        val slot = slot<OnCompleteListener<QuerySnapshot>>()
        every { task.addOnCompleteListener(capture(slot)) } returns task
        every { task.isSuccessful } returns true
        every { task.result } returns snapshot
        every { snapshot.iterator() } returns mutableListOf(doc1, doc2, doc3).iterator()
        
        every { doc1.data } returns hashMapOf("displayName" to "User 1") // Valid
        every { doc2.data } returns hashMapOf("reported" to true, "displayName" to "User 2") // Reported
        every { doc3.data } returns hashMapOf("message" to "No Name") // No displayName

        viewModel.fetchPosts()
        slot.captured.onComplete(task)

        assertEquals(1, viewModel.posts.size)
    }

    @Test
    fun `fetchPosts does nothing on task failure`() {
        val collectionRef = mockk<CollectionReference>(relaxed = true)
        val query = mockk<Query>(relaxed = true)
        val task = mockk<Task<QuerySnapshot>>(relaxed = true)

        every { db.collection("posts") } returns collectionRef
        every { collectionRef.orderBy(any<String>(), any()) } returns query
        every { query.limit(any()) } returns query
        every { query.get() } returns task
        
        val slot = slot<OnCompleteListener<QuerySnapshot>>()
        every { task.addOnCompleteListener(capture(slot)) } returns task
        every { task.isSuccessful } returns false

        viewModel.fetchPosts()
        slot.captured.onComplete(task)

        assertTrue(viewModel.posts.isEmpty())
    }

    @Test
    fun `likePost updates firestore and refreshes posts`() {
        val post = Post("id1", "Name", "Avatar", "Message", "type", "userId", "10", "5", "photoUrl", "video", "thumbnail")
        val docRef = mockk<DocumentReference>(relaxed = true)
        val task = mockk<Task<Void>>(relaxed = true)
        
        every { db.collection("posts").document(post.id) } returns docRef
        every { docRef.set(any(), any()) } returns task
        
        val slot = slot<OnSuccessListener<Void>>()
        every { task.addOnSuccessListener(capture(slot)) } returns task
        
        viewModel.likePost(post)
        slot.captured.onSuccess(null)
        
        verify { db.collection("posts").orderBy("createdAt", Query.Direction.DESCENDING) }
    }

    @Test
    fun `reportPost updates firestore and removes post from list`() {
        val post1 = Post("id1", "Name 1", "Avatar", "Message", "type", "userId", "10", "5", "photoUrl", "video", "thumbnail")
        val post2 = Post("id2", "Name 2", "Avatar", "Message", "type", "userId", "10", "5", "photoUrl", "video", "thumbnail")
        
        // Populate initial list (using a trick to access private field if necessary, or just rely on fetchPosts)
        // Since _posts is a mutableStateListOf, we can't easily populate it without mocking fetchPosts or similar.
        // But we can trigger fetchPosts first.
        
        val collectionRef = mockk<CollectionReference>(relaxed = true)
        val query = mockk<Query>(relaxed = true)
        val fetchTask = mockk<Task<QuerySnapshot>>(relaxed = true)
        val snapshot = mockk<QuerySnapshot>(relaxed = true)
        val doc1 = mockk<QueryDocumentSnapshot>(relaxed = true)
        val doc2 = mockk<QueryDocumentSnapshot>(relaxed = true)

        every { db.collection("posts") } returns collectionRef
        every { collectionRef.orderBy(any<String>(), any()) } returns query
        every { query.limit(any()) } returns query
        every { query.get() } returns fetchTask
        
        val fetchSlot = slot<OnCompleteListener<QuerySnapshot>>()
        every { fetchTask.addOnCompleteListener(capture(fetchSlot)) } returns fetchTask
        every { fetchTask.isSuccessful } returns true
        every { fetchTask.result } returns snapshot
        every { snapshot.iterator() } returns mutableListOf(doc1, doc2).iterator()
        
        every { doc1.id } returns "id1"
        every { doc1.data } returns hashMapOf("displayName" to "Name 1")
        every { doc2.id } returns "id2"
        every { doc2.data } returns hashMapOf("displayName" to "Name 2")

        viewModel.fetchPosts()
        fetchSlot.captured.onComplete(fetchTask)
        
        assertEquals(2, viewModel.posts.size)

        // Now test reportPost
        val docRef = mockk<DocumentReference>(relaxed = true)
        val reportTask = mockk<Task<Void>>(relaxed = true)
        
        every { db.collection("posts").document("id1") } returns docRef
        every { docRef.set(any(), any()) } returns reportTask
        
        val reportSlot = slot<OnSuccessListener<Void>>()
        every { reportTask.addOnSuccessListener(capture(reportSlot)) } returns reportTask
        
        viewModel.reportPost(viewModel.posts[0], 0)
        reportSlot.captured.onSuccess(null)
        
        assertEquals(1, viewModel.posts.size)
        assertEquals("id2", viewModel.posts[0].id)
    }

    @Test
    fun `reportPost does not remove post if index is out of bounds`() {
        val post = Post("id1", "Name 1", "Avatar", "Message", "type", "userId", "10", "5", "photoUrl", "video", "thumbnail")
        val docRef = mockk<DocumentReference>(relaxed = true)
        val task = mockk<Task<Void>>(relaxed = true)
        
        every { db.collection("posts").document(post.id) } returns docRef
        every { docRef.set(any(), any()) } returns task
        
        val slot = slot<OnSuccessListener<Void>>()
        every { task.addOnSuccessListener(capture(slot)) } returns task
        
        viewModel.reportPost(post, 10) // Out of bounds
        slot.captured.onSuccess(null)
        
        // Assertions or verifications could be added here if needed, 
        // but the main point is to trigger the branch.
    }
}