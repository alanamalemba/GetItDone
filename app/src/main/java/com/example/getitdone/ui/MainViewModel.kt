package com.example.getitdone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitdone.GetItDoneApplication
import com.example.getitdone.data.model.Task
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val taskRepository = GetItDoneApplication.taskRepository

    fun createTask(title: String, description: String?) {
        val task = Task(
            title = title, description = description
        )

        viewModelScope.launch {
            taskRepository.createTask(task)
        }
    }
}