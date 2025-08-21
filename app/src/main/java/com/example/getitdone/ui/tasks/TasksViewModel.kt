package com.example.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitdone.GetItDoneApplication
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.Task
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class TasksViewModel : ViewModel() {
    private val taskDao = GetItDoneApplication.taskDao

    suspend fun fetchAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
}