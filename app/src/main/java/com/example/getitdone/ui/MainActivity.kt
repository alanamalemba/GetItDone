package com.example.getitdone.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getitdone.R
import com.example.getitdone.data.model.TasksList
import com.example.getitdone.databinding.ActivityMainBinding
import com.example.getitdone.databinding.DialogAddTaskBinding
import com.example.getitdone.databinding.DialogAddTasksListBinding
import com.example.getitdone.databinding.TabButtonBinding
import com.example.getitdone.ui.tasks.StarredTasksFragment
import com.example.getitdone.ui.tasks.TasksFragment
import com.example.getitdone.util.InputValidator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var currentTasksList: List<TasksList> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {

            lifecycleScope.launch {
                viewModel.getTasksList().collectLatest { tasksLists ->
                    currentTasksList = tasksLists
                    viewPager2.adapter = PagerAdapter(this@MainActivity, tasksLists.size + 2)
                    viewPager2.currentItem = 1
                    TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                        when (position) {
                            0 -> tab.icon =
                                ContextCompat.getDrawable(this@MainActivity, R.drawable.icon_star)

                            tasksLists.size + 1 -> {
                                val buttonBinding = TabButtonBinding.inflate(layoutInflater)
                                tab.customView = buttonBinding.root
                                buttonBinding.root.setOnClickListener { showAddTasksListDialog() }
                            }

                            else -> tab.text = tasksLists[position - 1].name

                        }
                    }.attach()
                }
            }

            floatingActionButton.setOnClickListener { showAddTaskDialog() }
            setContentView(root)
        }
    }

    private fun showAddTasksListDialog() {
        val dialogBinding = DialogAddTasksListBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(this).setTitle("Add new list").setView(dialogBinding.root)
            .setPositiveButton("Create") { dialog, _ -> viewModel.addNewTalkList(dialogBinding.editTextListName.text.toString()) }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }.show()
    }

    private fun showAddTaskDialog() {
        DialogAddTaskBinding.inflate(layoutInflater).apply {
            val dialog = BottomSheetDialog(this@MainActivity)

            dialog.setContentView(root)

            buttonSave.isEnabled = false

            editTextTaskTitle.addTextChangedListener { editable ->

                buttonSave.isEnabled = InputValidator.checkIsInputValid(editable.toString())

            }

            imageButtonDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.isVisible) View.GONE else View.VISIBLE
            }

            buttonSave.setOnClickListener {
                if(currentTasksList.isNotEmpty()){dialog.dismiss()
                    val selectedTasksListId =
                        currentTasksList[binding.viewPager2.currentItem - 1].id
                    viewModel.createTask(
                        title = editTextTaskTitle.text.toString(),
                        description = editTextTaskDetails.text.toString(),
                        listId = selectedTasksListId
                    )
                }

                dialog.dismiss()

            }

            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity, private val numberOfPages: Int) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = numberOfPages

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StarredTasksFragment()
                else -> TasksFragment()
            }
        }
    }
}