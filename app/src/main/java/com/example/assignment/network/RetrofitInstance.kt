package com.example.assignment.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory // Correct the import statement

class RetrofitInstance {
    companion object{
        private const val BASE_URL="http://universities.hipolabs.com/"
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}