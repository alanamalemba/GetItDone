package com.example.getitdone.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.getitdone.data.model.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun createTask(task: Task)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks():List<Task>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}