package com.nexora.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nexora.app.NexoraApp
import com.nexora.app.data.model.Assignment
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AssignmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = (application as NexoraApp).assignmentRepository

    val assignments = repo.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val upcoming = repo.getUpcoming()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun save(assignment: Assignment) {
        viewModelScope.launch { repo.save(assignment) }
    }

    fun update(assignment: Assignment) {
        viewModelScope.launch { repo.update(assignment) }
    }

    fun delete(assignment: Assignment) {
        viewModelScope.launch { repo.delete(assignment) }
    }

    suspend fun getById(id: String): Assignment? = repo.getById(id)
}
