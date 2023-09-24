package com.example.assignment.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.WebViewActivity
import com.example.assignment.data.UniversityModel

class UniversityListAdapter(private val activity: Activity) : RecyclerView.Adapter<UniversityListAdapter.MyViewHolder>() {
    private var universityList: List<UniversityModel>?=null
 fun setUniversityList(universityList: List<UniversityModel>?){
     this.universityList=universityList
 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.university_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(universityList?.get(position)!!, activity)
    }

    override fun getItemCount(): Int {
        return if(universityList==null) 0
        else universityList?.size!!
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val universityName: TextView = view.findViewById(R.id.university_name)
        private val universityCountry = view.findViewById<TextView>(R.id.university_country)
        private val universityWebpage: TextView = view.findViewById(R.id.university_webpage)

        fun bind(data: UniversityModel, activity: Activity) {
            universityName.text = data.name
            universityCountry.text = data.country
            val websiteUrl = data.web_pages?.firstOrNull()
            universityWebpage.text = websiteUrl

            // Handle website link click
            universityWebpage.setOnClickListener {
                websiteUrl?.let {
                    val intent = Intent(activity, WebViewActivity::class.java)
                    intent.putExtra("WEBSITE_URL", websiteUrl)
                    activity.startActivity(intent)
                }
            }
        }
    }
}
