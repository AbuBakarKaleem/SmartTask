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
                    statusView.apply {
                        text = getString(string.unresolved)
                        setTextColor(requireContext().getColorStateListCompat(R.color.color_yellow))
                    }
                }

                1 -> {
                    statusView.apply {
                        text = getString(string.resolved)
                        setTextColor(requireContext().getColorStateListCompat(R.color.color_green))
                        statusImage.show()
                        resolveButton.gone()
                        unResolveButton.gone()
                    }
                }

                2 -> {
                    statusView.apply {
                        text = getString(string.not_resolved)
                        setTextColor(requireContext().getColorStateListCompat(R.color.color_red))
                        statusImage.setImageDrawable(requireContext().getDrawableCompat(R.drawable.unresolved_sign))
                        statusImage.show()
                        resolveButton.gone()
                        unResolveButton.gone()
                    }
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

}