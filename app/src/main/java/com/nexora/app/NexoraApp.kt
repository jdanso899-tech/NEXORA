package com.nexora.app

import android.app.Application
import com.nexora.app.data.local.AppDatabase
import com.nexora.app.data.repository.AssignmentRepository

class NexoraApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val assignmentRepository by lazy { AssignmentRepository(database.assignmentDao()) }
}
