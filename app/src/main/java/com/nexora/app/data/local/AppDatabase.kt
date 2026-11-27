package com.nexora.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nexora.app.data.model.Assignment
import com.nexora.app.data.model.Friend
import com.nexora.app.data.model.SharedAnswer

@Database(
    entities = [Assignment::class, Friend::class, SharedAnswer::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assignmentDao(): AssignmentDao
    abstract fun friendDao(): FriendDao
    abstract fun sharedAnswerDao(): SharedAnswerDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nexora.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
