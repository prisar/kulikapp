# Project Backlog

## Technical Debt

### Migrate GoogleSignInActivity to Activity Result API
**Priority:** Medium
**Location:** `app/src/main/java/com/agrohi/kulik/ui/screens/GoogleSignInActivity.kt:101-118, 163`

The `GoogleSignInActivity` currently uses deprecated `startActivityForResult()` and `onActivityResult()` methods. These should be migrated to the newer Activity Result API to avoid deprecation warnings and follow modern Android best practices.

**Current implementation:**
- Uses `startActivityForResult(signInIntent, RC_SIGN_IN)` (line 163)
- Overrides `onActivityResult()` to handle the result (lines 101-118)

**Recommended migration:**
- Use `registerForActivityResult()` with `ActivityResultContracts.StartActivityForResult()`
- Replace the deprecated methods with the modern activity result launcher

**References:**
- [Android Activity Result API Guide](https://developer.android.com/training/basics/intents/result)
