package com.agrohi.kulik.ui.screens

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AddPostViewModelTest {

    private lateinit var viewModel: AddPostViewModel
    private val db = mockk<FirebaseFirestore>(relaxed = true)
    private val auth = mockk<FirebaseAuth>(relaxed = true)
    private val currentUser = mockk<FirebaseUser>(relaxed = true)

    @Before
    fun setUp() {
        viewModel = AddPostViewModel(db, auth)
    }

    @Test
    fun `updateMessage updates the message state`() {
        val newMessage = "Hello World"
        viewModel.updateMessage(newMessage)
        assertEquals(newMessage, viewModel.message)
    }

    @Test
    fun `createPost calls onNotSignedIn when user is null`() {
        every { auth.currentUser } returns null
        val onNotSignedIn = mockk<() -> Unit>(relaxed = true)
        
        viewModel.createPost(
            onSuccess = {},
            onFailure = {},
            onNotSignedIn = onNotSignedIn
        )
        
        verify { onNotSignedIn() }
    }

    @Test
    fun `createPost attempts to add post when user is signed in`() {
        val message = "Test Post"
        viewModel.updateMessage(message)

        every { auth.currentUser } returns currentUser
        every { currentUser.uid } returns "user123"
        every { currentUser.displayName } returns "Test User"
        every { currentUser.photoUrl } returns mockk(relaxed = true)

        val collectionRef = mockk<CollectionReference>(relaxed = true)
        every { db.collection("posts") } returns collectionRef

        viewModel.createPost(
            onSuccess = {},
            onFailure = {},
            onNotSignedIn = {}
        )

        verify { collectionRef.add(any()) }
    }

    @Test
    fun `createPost calls onSuccess when post is added successfully`() {
        val message = "Test Post"
        viewModel.updateMessage(message)

        every { auth.currentUser } returns currentUser
        every { currentUser.uid } returns "user123"
        every { currentUser.displayName } returns "Test User"
        every { currentUser.photoUrl } returns mockk(relaxed = true)

        val documentRef = mockk<DocumentReference>(relaxed = true)
        val task = mockk<Task<DocumentReference>>(relaxed = true)
        val collectionRef = mockk<CollectionReference>(relaxed = true)

        every { collectionRef.add(any()) } returns task
        every { db.collection("posts") } returns collectionRef

        val successSlot = slot<OnSuccessListener<DocumentReference>>()
        every { task.addOnSuccessListener(capture(successSlot)) } answers {
            successSlot.captured.onSuccess(documentRef)
            task
        }
        every { task.addOnFailureListener(any()) } returns task

        var onSuccessCalled = false
        viewModel.createPost(
            onSuccess = { onSuccessCalled = true },
            onFailure = {},
            onNotSignedIn = {}
        )

        assertEquals(true, onSuccessCalled)
    }

    @Test
    fun `createPost calls onFailure when post addition fails`() {
        val message = "Test Post"
        viewModel.updateMessage(message)

        every { auth.currentUser } returns currentUser
        every { currentUser.uid } returns "user123"
        every { currentUser.displayName } returns "Test User"
        every { currentUser.photoUrl } returns mockk(relaxed = true)

        val task = mockk<Task<DocumentReference>>(relaxed = true)
        val collectionRef = mockk<CollectionReference>(relaxed = true)

        every { collectionRef.add(any()) } returns task
        every { db.collection("posts") } returns collectionRef

        val failureSlot = slot<OnFailureListener>()
        every { task.addOnSuccessListener(any()) } returns task
        every { task.addOnFailureListener(capture(failureSlot)) } answers {
            failureSlot.captured.onFailure(Exception("Test exception"))
            task
        }

        var onFailureCalled = false
        viewModel.createPost(
            onSuccess = {},
            onFailure = { onFailureCalled = true },
            onNotSignedIn = {}
        )

        assertEquals(true, onFailureCalled)
    }
}
