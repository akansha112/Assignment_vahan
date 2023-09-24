package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.assignment.adapter.UniversityListAdapter
import com.example.assignment.service.DataRefreshService
import com.example.assignment.viewmodel.ViewModelClass

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: UniversityListAdapter
    private lateinit var mainProgressBar: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainProgressBar = findViewById(R.id.progress_bar)
        initRecyclerView()
        initViewModel()
        startDataRefreshService()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.UniversityListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = UniversityListAdapter(this)
        recyclerView.adapter = recyclerAdapter
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[ViewModelClass::class.java]
        viewModel.progressBarVisibility.observe(this) { visibility ->
            mainProgressBar.visibility = visibility
        }

        viewModel.getLiveDataObserver().observe(this) {
            if (it != null) {
                recyclerAdapter.setUniversityList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.makeAPICall()
    }

    private fun startDataRefreshService() {
        val serviceIntent = Intent(this, DataRefreshService::class.java)
        try {
            startService(serviceIntent)
            Log.d("MyApp", "DataRefreshService started")
        } catch (e: Exception) {
            Log.e("MyApp", "Error starting DataRefreshService: ${e.message}")
        }
    }
}
