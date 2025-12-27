# Codebase Knowledge for Claude Code

This file contains important implementation details and patterns used in the Kulik app codebase.

## Profile Photo Update Implementation

**Location:** `app/src/main/java/com/agrohi/kulik/ui/screens/ProfileScreen.kt`

**Implementation Details:**
- Photo URL updates use Firebase Auth's `updateProfile()` method directly
- **No Firebase Storage dependency required** - users can provide any publicly accessible image URL
- Implementation uses `userProfileChangeRequest` builder (line 148-150)

**How it works:**
1. User clicks "Change Photo" button
2. TextField appears for URL input
3. On Save, calls `currentUser.updateProfile()` with new photoUri
4. Updates local `avatar` state on success
5. Shows toast notification for feedback

**Key Code Pattern:**
```kotlin
val profileUpdates = userProfileChangeRequest {
    photoUri = android.net.Uri.parse(newPhotoUrl)
}
currentUser.updateProfile(profileUpdates)
    .addOnCompleteListener { task ->
        if (task.isSuccessful) {
            avatar = newPhotoUrl
            Toast.makeText(context, "Photo updated", Toast.LENGTH_SHORT).show()
        }
    }
```

**Design Decision:**
- Chose URL-based approach over Firebase Storage to minimize dependencies and complexity
- Users can host images on any service (Imgur, Google Photos, etc.)
- Keeps implementation lightweight and flexible

## Explore Screen Implementation

**Location:** `app/src/main/java/com/agrohi/kulik/ui/screens/ExploreScreen.kt`

**Implementation Details:**
- Uses Firebase Firestore to fetch real posts data
- Displays posts in a 3-column grid layout using `LazyVerticalGrid`
- Implements `ExploreViewModel` for data management (similar pattern to `FeedViewModel`)
- Uses Glide for remote image loading

**Architecture Pattern:**
1. **ViewModel** (`ExploreViewModel.kt`) - Handles Firebase data fetching
2. **Screen** (`ExploreScreen.kt`) - Composable UI with LazyColumn containing header, explore cards, and grid
3. **PostCard** - Compact grid item showing post image/message and likes

**Key Code Pattern:**
```kotlin
// ViewModel fetches posts from Firebase
class ExploreViewModel(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts

    fun fetchPosts() {
        db.collection("posts")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(100)
            .get()
            // ... process results
    }
}

// Screen uses LazyVerticalGrid for 3-column layout
LazyVerticalGrid(
    columns = GridCells.Fixed(3),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(viewModel.posts) { post ->
        PostCard(post = post)
    }
}

// PostCard uses GlideImage for remote images
GlideImage(
    model = post.photoUrl,
    contentDescription = post.message,
    contentScale = ContentScale.Crop,
    modifier = Modifier.fillMaxWidth().height(100.dp)
)
```

**Design Decisions:**
- Reuses `com.agrohi.kulik.model.Post` model from FeedScreen for consistency
- Grid layout (3 columns) optimized for browsing many posts quickly
- Compact cards (100dp image height) with minimal info (message + likes only)
- Falls back to colored background with text for posts without images
- Uses same Firebase query pattern as FeedScreen (orders by `createdAt` DESC, filters reported posts)
