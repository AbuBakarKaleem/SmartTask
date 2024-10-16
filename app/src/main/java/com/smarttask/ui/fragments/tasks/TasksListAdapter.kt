package com.smarttask.ui.fragments.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smarttask.data.remote.model.ResponseModel.Tasks
import com.smarttask.databinding.ItemTasksBinding

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
                itemBinding.root.setOnClickListener { listener.invoke(model) }

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
