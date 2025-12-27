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
