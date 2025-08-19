package com.example.getitdone.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.Task
import com.example.getitdone.data.TaskDao
import com.example.getitdone.databinding.ActivityMainBinding
import com.example.getitdone.databinding.DialogAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.getitdone.ui.tasks.TasksFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    private lateinit var database: GetItDoneDatabase
    private val taskDao: TaskDao by lazy { database.getTaskDao() }
    private val tasksFragment: TasksFragment = TasksFragment()

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

        database = GetItDoneDatabase.getDatabaseInstance(this)

        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.floatingActionButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        DialogAddTaskBinding.inflate(layoutInflater).apply {
            val dialog = BottomSheetDialog(this@MainActivity)

            dialog.setContentView(root)

            buttonSave.isEnabled = false

            editTextTaskTitle.addTextChangedListener { editable ->

                buttonSave.isEnabled = !editable.isNullOrEmpty()

            }

            imageButtonDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.isVisible) View.GONE else View.VISIBLE
            }

            buttonSave.setOnClickListener {
                val task = Task(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString()
                );

                thread {
                    taskDao.createTask(task)
                }
                dialog.dismiss()
                tasksFragment.fetchAllTasks()

            }

            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }
    }
}