package com.example.getitdone.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = TasksList::class,
        parentColumns = ["id"],
        childColumns = ["list_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    @ColumnInfo("is_starred") val isStarred: Boolean = false,
    @ColumnInfo("is_complete") val isComplete: Boolean = false,
    @ColumnInfo(name = "list_id") val listId: Int
)
