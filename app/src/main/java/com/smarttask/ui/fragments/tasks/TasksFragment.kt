package com.smarttask.ui.fragments.tasks

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.smarttask.base.BaseFragment
import com.smarttask.data.remote.model.UIState
import com.smarttask.databinding.FragmentTasksBinding
import com.smarttask.extensions.gone
import com.smarttask.extensions.show
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TasksFragment : BaseFragment<FragmentTasksBinding>(FragmentTasksBinding::inflate) {
    private val viewModel: TasksViewModel by viewModels()

    private lateinit var adpater: TasksListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserveListener()
    }

    private fun setupViews() = with(binding) {
        adpater = TasksListAdapter { tasks ->
            Toast.makeText(requireContext(), "Data" + tasks.id, Toast.LENGTH_SHORT).show()
        }
        taskListView.adapter = adpater
    }

    private fun setupObserveListener() = with(viewModel) {
        tasksLD.observe(viewLifecycleOwner) { res ->
            if (res.isEventNotConsumed) {
                if (res.tasks.isNotEmpty()) {
                    adpater.differ.submitList(res.tasks)
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
}