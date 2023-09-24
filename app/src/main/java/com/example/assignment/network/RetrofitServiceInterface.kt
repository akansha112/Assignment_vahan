package com.example.assignment.network


import com.example.assignment.data.UniversityModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServiceInterface {
    @GET("search")
    fun getUniversityList(): Call<List<UniversityModel>>
}