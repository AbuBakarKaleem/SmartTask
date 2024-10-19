package com.smarttask.ui.fragments.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smarttask.R
import com.smarttask.data.remote.model.ResponseModel.Tasks
import com.smarttask.databinding.ItemTasksBinding
import com.smarttask.extensions.getColorStateListCompat
import com.smarttask.extensions.getDrawableCompat
import com.smarttask.extensions.show

class TasksListAdapter(private val listener: (Tasks) -> Unit) :
    RecyclerView.Adapter<TasksListAdapter.TasksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding = ItemTasksBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(differ.currentList[position], position)
    }

    override fun getItemCount() = differ.currentList.size


    inner class TasksViewHolder(private val itemBinding: ItemTasksBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: Tasks, position: Int) {
            itemBinding.apply {
                titleView.text = model.title
                dueDateView.text = model.formatedDueDate
                daysLeftView.text = model.daysLeft
                if (model.status == 1) {
                    updateTaskViewColors(itemBinding, R.color.color_green)
                    statusIcon.setImageDrawable(itemBinding.root.context.getDrawableCompat(R.drawable.btn_resolved))
                    statusIcon.show()
                } else if (model.status == 2) {
                    updateTaskViewColors(itemBinding, R.color.color_red)
                    statusIcon.setImageDrawable(itemBinding.root.context.getDrawableCompat(R.drawable.btn_unresolved))
                    statusIcon.show()
                }
                itemBinding.root.setOnClickListener { listener.invoke(model) }

            }
        }

        private fun updateTaskViewColors(binding: ItemTasksBinding, colorRes: Int) {
            binding.apply {
                val color = itemBinding.root.context.getColorStateListCompat(colorRes)
                titleView.setTextColor(color)
                daysLeftView.setTextColor(color)
                dueDateView.setTextColor(color)
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Tasks>() {

        override fun areItemsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
            return oldItem.id == newItem.id
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
}
