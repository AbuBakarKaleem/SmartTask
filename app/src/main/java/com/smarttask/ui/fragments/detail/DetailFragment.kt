package com.smarttask.ui.fragments.detail

import android.os.Bundle
import android.view.View
import com.smarttask.R
import com.smarttask.R.string
import com.smarttask.base.BaseFragment
import com.smarttask.data.remote.model.ResponseModel.Tasks
import com.smarttask.databinding.FragmentDetailBinding
import com.smarttask.extensions.getColorStateListCompat
import com.smarttask.extensions.getDrawableCompat
import com.smarttask.extensions.gone
import com.smarttask.extensions.longToast
import com.smarttask.extensions.navigateUp
import com.smarttask.extensions.setNavigationResult
import com.smarttask.extensions.show


class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private lateinit var selectedTasks: Tasks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedTasks = DetailFragmentArgs.fromBundle(it).task
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        backView.setOnClickListener { navigateUp() }

        with(selectedTasks) {
            titleView.text = title
            daysLeftView.text = daysLeft
            dueDateView.text = formatedDueDate
            descriptionView.text = description

            when (status) {
                0 -> {
                    updateStatusView(getString(string.unresolved), R.color.color_yellow)
                }

                1 -> {
                    updateStatusView(getString(string.resolved), R.color.color_green)
                    statusImage.show()
                    resolveButton.gone()
                    unResolveButton.gone()
                    updateTaskViewColors(R.color.color_green)
                }

                2 -> {
                    updateStatusView(getString(string.not_resolved), R.color.color_red)
                    statusImage.setImageDrawable(requireContext().getDrawableCompat(R.drawable.unresolved_sign))
                    statusImage.show()
                    resolveButton.gone()
                    unResolveButton.gone()
                    updateTaskViewColors(R.color.color_red)
                }

                else -> {
                    // do nothing
                }
            }

            resolveButton.setOnClickListener {
                longToast(getString(string.status_updated_message))
                setNavigationResult(
                    "UpdateStatus", mapOf("id" to selectedTasks.id, "status" to "1")
                )
                navigateUp()

            }
            unResolveButton.setOnClickListener {
                longToast(getString(string.status_updated_message))
                setNavigationResult(
                    "UpdateStatus", mapOf("id" to selectedTasks.id, "status" to "2")
                )
                navigateUp()
            }
        }


    }

    private fun updateTaskViewColors(colorRes: Int) = with(binding) {
        val color = requireContext().getColorStateListCompat(colorRes)
        titleView.setTextColor(color)
        daysLeftView.setTextColor(color)
        dueDateView.setTextColor(color)
    }

    private fun updateStatusView(status: String, colorRes: Int) {
        binding.statusView.apply {
            text = status
            setTextColor(requireContext().getColorStateListCompat(colorRes))
        }
    }

}