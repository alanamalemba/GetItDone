package com.example.getitdone

import android.app.Application
import com.example.getitdone.data.TaskRepository
import com.example.getitdone.data.database.GetItDoneDatabase
import com.example.getitdone.data.database.TaskDao

class GetItDoneApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = GetItDoneDatabase.getDatabaseInstance(this)
        val taskDao = database.getTaskDao()
        taskRepository = TaskRepository(taskDao)
    }

    companion object {
        lateinit var taskRepository: TaskRepository
    }
}    