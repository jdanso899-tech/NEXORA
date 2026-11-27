package com.nexora.app.data.repository

import com.nexora.app.data.local.AssignmentDao
import com.nexora.app.data.model.Assignment
import kotlinx.coroutines.flow.Flow

class AssignmentRepository(private val dao: AssignmentDao) {
    fun getAll(): Flow<List<Assignment>> = dao.getAll()
    fun getUpcoming(): Flow<List<Assignment>> = dao.getUpcoming()
    suspend fun getById(id: String) = dao.getById(id)
    suspend fun save(assignment: Assignment) = dao.insert(assignment)
    suspend fun update(assignment: Assignment) = dao.update(assignment)
    suspend fun delete(assignment: Assignment) = dao.delete(assignment)
    suspend fun deleteById(id: String) = dao.deleteById(id)
}
