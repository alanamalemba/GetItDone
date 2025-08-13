package com.example.getitdone

import TasksFragment
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.ActivityMainBinding
import com.example.getitdone.databinding.DialogAddTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager2.adapter = PagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Tasks"
                }
            }
        }.attach()

        setUpClickListeners()

        val database = GetItDoneDatabase.createDatabase(this)

        val taskDao = database.getTaskDao()

        thread {
            taskDao.createTask(Task(title = "Alan's first task!"))

            val tasks = taskDao.getAllTasks()

            runOnUiThread {
                Toast.makeText(this, "Number of tasks: ${tasks.size}", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun setUpClickListeners() {
        binding.floatingActionButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(this).setTitle("Add new task").setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                Toast.makeText(
                    this, "Your task is ${dialogBinding.editText.text}", Toast.LENGTH_LONG
                ).show()
            }.setNegativeButton("Cancel", null).show()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }
    }
}