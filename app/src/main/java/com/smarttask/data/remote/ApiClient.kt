package com.smarttask.data.remote

import com.smarttask.helper.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val networkInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder().build()
        chain.proceed(request)
    }

    private val httpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(networkInterceptor)
            .build()

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(Constants.END_POINT).client(httpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
