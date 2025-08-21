package com.example.getitdone.ui

import android.app.ActivityManager.TaskDescription
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitdone.GetItDoneApplication
import com.example.getitdone.data.Task
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    private val taskDao = GetItDoneApplication.taskDao

    fun createTask(title: String, description: String?) {
        val task = Task(
            title = title, description = description
        )

        viewModelScope.launch {
            taskDao.createTask(task)
        }
    }
}