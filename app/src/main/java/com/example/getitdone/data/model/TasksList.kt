package com.example.getitdone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks_lists")
data class TasksList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
