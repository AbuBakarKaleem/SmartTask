package com.smarttask.data.repository

import com.smarttask.data.remote.ApiClient
import com.smarttask.data.remote.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Repository {
    private val service = ApiClient.apiService

    suspend fun fetchTasks() = flow {
        val response = service.getTasks()
        emit(
            if (response.isSuccessful) {
                DataState.Success(response.body())
            } else {
                val errorBody = response.errorBody()?.string()
                DataState.Error(DataState.CustomMessages.Unauthorized(errorBody.toString()))
            }
        )
    }.catch {
        this.emit(
            DataState.Error(
                DataState.CustomMessages.SomethingWentWrong(
                    it.message ?: DataState.CustomMessages.BadRequest.toString()
                )
            )
        )
    }

    companion object {
        private var INSTANCE: Repository? = null
        fun getInstance() = INSTANCE ?: Repository().also { INSTANCE = it }
    }
}