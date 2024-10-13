package com.smarttask.ui.fragments.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.smarttask.databinding.FragmentTasksBinding
import com.smarttask.base.BaseFragment

class TasksFragment : BaseFragment<FragmentTasksBinding>(FragmentTasksBinding::inflate) {
    private val viewModel: TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}