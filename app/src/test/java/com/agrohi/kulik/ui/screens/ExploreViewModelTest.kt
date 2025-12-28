package com.agrohi.kulik.ui.screens

import com.agrohi.kulik.model.Post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExploreViewModelTest {

    private lateinit var viewModel: ExploreViewModel
    private val db = mockk<FirebaseFirestore>(relaxed = true)

    @Before
    fun setUp() {
        viewModel = ExploreViewModel(db)
    }

    @Test
    fun `fetchPosts populates the posts list on success`() {
        val querySnapshot = mockk<QuerySnapshot>()
        val task = mockk<Task<QuerySnapshot>>()
        val query = mockk<Query>(relaxed = true)
        val document = mockk<QueryDocumentSnapshot>()

        every { db.collection("posts").orderBy("createdAt", Query.Direction.DESCENDING).limit(100) } returns query
        every { query.get() } returns task

        val slot = slot<OnCompleteListener<QuerySnapshot>>()
        every { task.addOnCompleteListener(capture(slot)) } answers {
            every { task.isSuccessful } returns true
            every { task.result } returns querySnapshot
            every { querySnapshot.iterator() } returns mutableListOf(document).iterator()
            every { document.id } returns "1"
            every { document.data } returns mapOf(
                "displayName" to "user1",
                "avatar" to "avatar1",
                "message" to "message1",
                "type" to "text",
                "userId" to "uid1",
                "views" to "10",
                "likes" to "5",
                "photoUrl" to "",
                "video" to "",
                "reported" to false
            )
            slot.captured.onComplete(task)
            task
        }

        viewModel.fetchPosts()

        assertEquals(1, viewModel.posts.size)
        assertEquals("user1", viewModel.posts[0].name)
    }
}
