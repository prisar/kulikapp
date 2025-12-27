Kotlin Error: Migration to compilerOptions DSL
Error Message
e: ... build.gradle.kts:39:9: Using 'jvmTarget: String' is an error. Please migrate to the compilerOptions DSL.

Context
This error occurs when upgrading a project to Kotlin 2.0.0 or higher. The older kotlinOptions block (which accepted string values like "11") has been deprecated and turned into an error in favor of the type-safe compilerOptions DSL.

The Problem
In older Gradle scripts, we used:

Kotlin
// DEPRECATED/ERROR in Kotlin 2.0+
kotlinOptions {
    jvmTarget = "11"
}
The Solution
You must move the configuration to the kotlin extension and use the JvmTarget enum instead of a string.

1. Update the Gradle Script

Remove the kotlinOptions block from inside the android { ... } block and add the following at the top level of your build.gradle.kts:

Kotlin
android {
    // ... other settings ...

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    
    // REMOVE THIS:
    // kotlinOptions { jvmTarget = "11" } 
}

// ADD THIS INSTEAD:
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}
2. Alternative (Inline with Android block)

If you prefer to keep it inside the android block for organizational reasons, you can use the newer syntax there, though the top-level kotlin block is the recommended standard for Kotlin 2.0:

Kotlin
android {
    // ...
    kotlinOptions {
        // Use the set() method with the Enum
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}
Summary of Changes
Old Way (Kotlin < 2.0)	New Way (Kotlin 2.0+)
jvmTarget = "11"	jvmTarget.set(JvmTarget.JVM_11)
Uses String	Uses org.jetbrains.kotlin.gradle.dsl.JvmTarget
Defined in kotlinOptions	Defined in compilerOptions