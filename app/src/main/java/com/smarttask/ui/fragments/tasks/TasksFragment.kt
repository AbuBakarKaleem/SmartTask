package com.smarttask.ui.fragments.tasks

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.smarttask.R
import com.smarttask.base.BaseFragment
import com.smarttask.data.remote.model.UIState
import com.smarttask.databinding.FragmentTasksBinding
import com.smarttask.extensions.getNavigationResult
import com.smarttask.extensions.gone
import com.smarttask.extensions.navigateTo
import com.smarttask.extensions.show
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TasksFragment : BaseFragment<FragmentTasksBinding>(FragmentTasksBinding::inflate) {
    private val viewModel: TasksViewModel by viewModels()

    private lateinit var adapter: TasksListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        setupViews()
        setupObserveListener()
        setupNavigateResultListener()
    }

    private fun setupViews() = with(binding) {
        adapter = TasksListAdapter { task ->
            navigateTo(TasksFragmentDirections.toDetailFragment(task))
        }
        taskListView.adapter = adapter
    }

    private fun setupObserveListener() = with(viewModel) {
        tasksLD.observe(viewLifecycleOwner) { res ->
            if (res.isEventNotConsumed) {
                if (res.tasks.isNotEmpty()) {
                    adapter.differ.submitList(res.tasks)
                } else {
                    binding.apply {
                        taskListView.gone()
                        noTaskGroup.show()
                    }
                }
            }
        }
        lifecycleScope.launch {
            uiState.collectLatest { uiState ->
                when (uiState) {
                    is UIState.LoadingState -> {
                        binding.progressBar.show()
                    }

                    is UIState.ContentState, UIState.InitialState -> {
                        binding.progressBar.gone()
                    }

                    is UIState.ErrorState -> {
                        binding.apply {
                            progressBar.gone()
                            taskListView.gone()
                            noTaskGroup.show()
                        }
                        showAlertDialog(messages = uiState.message,
                            onPosClick = { _, _ -> },
                            onNegClick = { _, _ -> })
                    }
                }
            }
        }
    }

    private fun setupNavigateResultListener() {
        getNavigationResult<Map<String, String>>(R.id.tasksFragment, "UpdateStatus", onResult = {
            val taskId = it["id"]
            val updatedStatus = it["status"]!!.toInt()
            val updatedItemIndex =
                adapter.differ.currentList.indexOfFirst { item -> item.id == taskId }

            if (updatedItemIndex != -1) {
                val updatedItem = adapter.differ.currentList[updatedItemIndex].apply {
                    status = updatedStatus
                }
                val updatedList = adapter.differ.currentList.toMutableList()
                updatedList[updatedItemIndex] = updatedItem
                adapter.differ.submitList(updatedList)
            }
        })
    }
}