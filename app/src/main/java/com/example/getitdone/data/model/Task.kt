package com.example.getitdone.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    @ColumnInfo("is_starred") val isStarred: Boolean = false,
    @ColumnInfo("is_complete") val isComplete: Boolean = false
)
