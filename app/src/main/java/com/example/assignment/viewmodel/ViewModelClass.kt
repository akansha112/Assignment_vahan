package com.example.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.data.UniversityModel
import com.example.assignment.network.RetrofitInstance
import com.example.assignment.network.RetrofitServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelClass : ViewModel() {
    lateinit var liveDataList: MutableLiveData<List<UniversityModel>>

    init {
        liveDataList = MutableLiveData()
    }
    val progressBarVisibility = MutableLiveData<Int>().apply { value = android.view.View.GONE }

    fun getLiveDataObserver(): MutableLiveData<List<UniversityModel>>{
        return liveDataList
    }
    //function to make an API call
    fun makeAPICall(){
        progressBarVisibility.postValue(android.view.View.VISIBLE)

        val retrofitInstance=RetrofitInstance.getRetrofitInstance()
        val retroService=retrofitInstance.create(RetrofitServiceInterface::class.java)
        val call=retroService.getUniversityList()
        call.enqueue(object : Callback<List<UniversityModel>> {
            override fun onFailure(call: Call<List<UniversityModel>>, t: Throwable) {
                //Hide the progress bar on failure
                progressBarVisibility.postValue(android.view.View.GONE)

                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<List<UniversityModel>>,
                response: Response<List<UniversityModel>>
            ) {
                // Hide the progress bar on success
                progressBarVisibility.postValue(android.view.View.GONE)
                if (response.isSuccessful) {
                    liveDataList.postValue(response.body())
                } else {
                    // Handle the case where the response is not successful
                    progressBarVisibility.postValue(android.view.View.GONE)
                    liveDataList.postValue(null)
                }
            }
                    })
    }
}
