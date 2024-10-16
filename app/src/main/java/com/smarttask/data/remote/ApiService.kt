package com.smarttask.data.remote

import com.smarttask.data.remote.model.ResponseModel.TaskResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getTasks(@Url endPoint: String = ".mockable.io"): Response<TaskResponse>
}