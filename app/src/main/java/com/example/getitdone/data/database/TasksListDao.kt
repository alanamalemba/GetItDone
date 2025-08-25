package com.example.getitdone.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.getitdone.data.model.TasksList
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksListDao {
    @Insert
    suspend fun createTasksList(tasksList: TasksList)

    @Query("SELECT * FROM tasks_lists")
    fun getTasksLists(): Flow<List<TasksList>>

    @Update
    suspend fun updateTasksList(tasksList: TasksList)

    @Delete
    suspend fun deleteTasksList(tasksList: TasksList)
}