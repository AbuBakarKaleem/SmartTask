package com.smarttask.ui.fragments.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smarttask.data.remote.DataState
import com.smarttask.data.remote.model.ResponseModel.TaskResponse
import com.smarttask.data.remote.model.UIState
import com.smarttask.data.usecase.FetchTasksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksViewModel : ViewModel() {
    private val tasksUseCases = FetchTasksUseCase()

    private val _tasks = MutableLiveData<TaskResponse>()
    val tasksLD: LiveData<TaskResponse> = _tasks

    private val _uiState = MutableStateFlow<UIState>(UIState.InitialState)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        getTasks()
    }


    fun getTasks() {
        _uiState.value = UIState.LoadingState
        viewModelScope.launch(Dispatchers.IO) {
            tasksUseCases.invoke().collect { dataState ->
                withContext(Dispatchers.Main) {
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.value = UIState.ContentState
                            _tasks.value = dataState.data!!
                        }

                        is DataState.Error -> {
                            _uiState.value = UIState.ErrorState(dataState.error.message)
                        }
                    }
                }
            }
        }
    }

}