package com.smarttask.data.repository

import com.smarttask.data.remote.ApiClient

class Repository {
    private val service = ApiClient.apiService

    companion object {
        private var INSTANCE: Repository? = null
        fun getInstance() = INSTANCE ?: Repository().also { INSTANCE = it }
    }
}