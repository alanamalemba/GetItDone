package com.example.getitdone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitdone.GetItDoneApplication
import com.example.getitdone.data.model.Task
import com.example.getitdone.data.model.TasksList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val taskRepository = GetItDoneApplication.taskRepository

    fun getTasksList(): Flow<List<TasksList>> {
        return taskRepository.getTasksList()
    }

    fun createTask(title: String, description: String?) {
        val task = Task(
            title = title, description = description
        )

        viewModelScope.launch {
            taskRepository.createTask(task)
        }
    }
}