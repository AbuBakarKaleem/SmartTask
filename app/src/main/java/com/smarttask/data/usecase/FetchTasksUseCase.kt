package com.smarttask.data.usecase

import com.smarttask.data.remote.DataState
import com.smarttask.data.remote.model.ResponseModel.TaskResponse
import com.smarttask.data.repository.Repository
import com.smarttask.extensions.toFormattedDateAndDaysLeft
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchTasksUseCase {
    private val repository = Repository.getInstance()
    suspend fun invoke(): Flow<DataState<TaskResponse>> = flow {
        repository.fetchTasks().collect { response ->
            when (response) {
                is DataState.Success -> {
                    response.data?.let {
                        response.data.tasks.forEach { tasks ->
                            val info = tasks.dueDate.toFormattedDateAndDaysLeft(tasks.targetDate)
                            tasks.formatedDueDate = info.first
                            tasks.daysLeft = info.second
                        }
                        response.data.tasks.sortedBy { it.priorty }
                        emit(
                            DataState.Success(response.data)
                        )
                    }
                }

                is DataState.Error -> {
                    emit(DataState.Error(response.error))
                }
            }
        }
    }
}