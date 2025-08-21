package com.example.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitdone.GetItDoneApplication
import com.example.getitdone.data.model.Task
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {

    private val tasksRepository = GetItDoneApplication.taskRepository

    suspend fun fetchAllTasks(): List<Task> {
        return tasksRepository.getTasks()
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.deleteTask(task)
        }
    }
}