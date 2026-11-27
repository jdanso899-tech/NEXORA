package com.nexora.app.data.local

import androidx.room.*
import com.nexora.app.data.model.Assignment
import com.nexora.app.data.model.Friend
import com.nexora.app.data.model.SharedAnswer
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {
    @Query("SELECT * FROM assignments ORDER BY dueDateTime ASC")
    fun getAll(): Flow<List<Assignment>>

    @Query("SELECT * FROM assignments WHERE id = :id")
    suspend fun getById(id: String): Assignment?

    @Query("SELECT * FROM assignments WHERE isCompleted = 0 AND dueDateTime > :now ORDER BY dueDateTime ASC LIMIT 5")
    fun getUpcoming(now: Long = System.currentTimeMillis()): Flow<List<Assignment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assignment: Assignment)

    @Update
    suspend fun update(assignment: Assignment)

    @Delete
    suspend fun delete(assignment: Assignment)

    @Query("DELETE FROM assignments WHERE id = :id")
    suspend fun deleteById(id: String)
}

@Dao
interface FriendDao {
    @Query("SELECT * FROM friends ORDER BY displayName ASC")
    fun getAll(): Flow<List<Friend>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(friends: List<Friend>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(friend: Friend)

    @Query("DELETE FROM friends")
    suspend fun clear()
}

@Dao
interface SharedAnswerDao {
    @Query("SELECT * FROM shared_answers WHERE toPhone = :phone ORDER BY sharedAt DESC")
    fun getReceived(phone: String): Flow<List<SharedAnswer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shared: SharedAnswer)
}
