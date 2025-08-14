package com.example.getitdone.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.ItemTaskBinding
import com.google.android.material.checkbox.MaterialCheckBox

class TasksAdapter(private val tasks: List<Task>, private val listener: TaskUpdatedListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    override fun getItemCount(): Int = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemTaskBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.checkboxDone.isChecked = task.isComplete
            binding.checkboxStar.isChecked = task.isStarred
            if (task.isComplete) {
                binding.textViewTaskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.textViewTaskDescription.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            binding.textViewTaskTitle.text = task.title
            binding.textViewTaskDescription.text = task.description
            binding.checkboxDone.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> {
                        task.copy(isComplete = true)
                    }

                    else -> {
                        task.copy(isComplete = false)
                    }
                }
                listener.onTaskUpdated(updatedTask)

            }

            binding.checkboxStar.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> {
                        task.copy(isStarred = true)
                    }

                    else -> {
                        task.copy(isStarred = false)
                    }
                }
                listener.onTaskUpdated(updatedTask)

            }
        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }
}