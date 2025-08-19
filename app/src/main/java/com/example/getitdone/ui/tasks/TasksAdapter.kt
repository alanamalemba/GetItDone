package com.example.getitdone.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.ItemTaskBinding

class TasksAdapter(private val listener: TaskItemClickListener) :
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
        this.tasks = tasks.sortedBy {
            it.isComplete
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                root.setOnLongClickListener {
                listener.onTaskDeleted(task)
                    true
                }
                checkboxDone.isChecked = task.isComplete
                checkboxStar.isChecked = task.isStarred
                if (task.isComplete) {
                    textViewTaskTitle.paintFlags =
                        textViewTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    textViewTaskDescription.paintFlags =
                        textViewTaskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewTaskTitle.paintFlags = 0;
                    textViewTaskDescription.paintFlags = 0;
                }
                textViewTaskTitle.text = task.title
                if (task.description.isNullOrEmpty()) {
                    textViewTaskDescription.visibility = View.GONE
                } else {
                    textViewTaskDescription.text = task.description
                    textViewTaskDescription.visibility = View.VISIBLE
                }
                checkboxDone.setOnClickListener {
                    val updatedTask = task.copy(isComplete = checkboxDone.isChecked)
                    listener.onTaskUpdated(updatedTask)
                }
                checkboxStar.setOnClickListener {
                    val updatedTask = task.copy(isStarred = binding.checkboxStar.isChecked)
                    listener.onTaskUpdated(updatedTask)
                }
            }
        }
    }

    interface TaskItemClickListener {
        fun onTaskUpdated(task: Task)

        fun onTaskDeleted(task:Task)
    }
}