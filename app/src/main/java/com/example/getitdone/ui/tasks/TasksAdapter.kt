package com.example.getitdone.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.ItemTaskBinding
import com.google.android.material.checkbox.MaterialCheckBox

class TasksAdapter(private val listener: TaskUpdatedListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()

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

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.checkboxDone.isChecked = task.isComplete
            binding.checkboxStar.isChecked = task.isStarred
            if (task.isComplete) {
                binding.textViewTaskTitle.paintFlags =
                    binding.textViewTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.textViewTaskDescription.paintFlags =
                    binding.textViewTaskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.textViewTaskTitle.paintFlags = 0;
                binding.textViewTaskDescription.paintFlags = 0;
            }
            binding.textViewTaskTitle.text = task.title
            binding.textViewTaskDescription.text = task.description
            binding.checkboxDone.setOnClickListener {
                val updatedTask = task.copy(isComplete = binding.checkboxDone.isChecked)
                listener.onTaskUpdated(updatedTask)
            }
            binding.checkboxStar.setOnClickListener {
                val updatedTask = task.copy(isStarred = binding.checkboxStar.isChecked)
                listener.onTaskUpdated(updatedTask)
            }

        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }
}