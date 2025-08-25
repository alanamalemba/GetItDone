package com.example.getitdone.data

import com.example.getitdone.data.database.TaskDao
import com.example.getitdone.data.database.TasksListDao
import com.example.getitdone.data.model.Task
import com.example.getitdone.data.model.TasksList
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao, private val tasksListDao: TasksListDao) {
    suspend fun createTask(task: Task) {
        taskDao.createTask(task)
    }

    fun getTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getStarredTasks(): Flow<List<Task>> {
        return taskDao.getStarredTasks()
    }

    fun getTasksList(): Flow<List<TasksList>> {
        return tasksListDao.getTasksLists()
    }

    suspend fun createTaskList(listName: String) {
        tasksListDao.createTasksList(TasksList(name = listName))
    }
}