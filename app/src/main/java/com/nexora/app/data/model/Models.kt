package com.nexora.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "assignments")
data class Assignment(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val subject: String = "",
    val description: String = "",
    val dueDateTime: Long,                 // epoch millis
    val isCompleted: Boolean = false,
    val voiceNotePath: String? = null,     // local file path
    val answerText: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "friends")
data class Friend(
    @PrimaryKey val phoneNumber: String,   // normalized E.164 or digits
    val displayName: String,
    val photoUri: String? = null,
    val isNexoraUser: Boolean = false,
    val lastSharedAt: Long? = null
)

@Entity(tableName = "shared_answers")
data class SharedAnswer(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val assignmentId: String,
    val fromPhone: String,
    val toPhone: String,
    val answerText: String,
    val sharedAt: Long = System.currentTimeMillis()
)

data class UserProfile(
    val displayName: String = "Student",
    val photoUri: String? = null,
    val bio: String = "",
    val phoneNumber: String = ""
)

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val role: String,          // "user" or "assistant"
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
