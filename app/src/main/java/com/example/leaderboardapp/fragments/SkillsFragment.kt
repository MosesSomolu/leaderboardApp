package com.example.leaderboardapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.leaderboardapp.R
import com.example.leaderboardapp.api.ApiService
import com.example.leaderboardapp.models.SkillDetails
import kotlinx.android.synthetic.main.learner_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SkillsFragment: Fragment() {

    private lateinit var recyclerView2: RecyclerView

    // COMPANION OBJECT CREATED. IT IS NEEDED TO LINK FRAGMENT TO ITS
    // MAIN ACTIVITY
    companion object { fun newInstance(): LearnersFragment { return LearnersFragment() }}

    //DEFAULT FRAGMENT METHODS TO OVERRIDE
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment with its respective xml file.
        return inflater.inflate(R.layout.fragment_skills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView2 = view.findViewById(R.id.skillRecycler)
        getRetrofitSkills()
    }


    // RECYCLER VIEW ADAPTER FOR SKILLSFRAGMENT
    class SkillAdapter(private val skills : List<SkillDetails>)
        : RecyclerView.Adapter<SkillAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.skill_item, parent, false)
            return ViewHolder(itemView = view)
        }

        override fun getItemCount(): Int = skills.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            return holder.bind(skills[position])
        }

        class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            private val name: TextView = itemView.findViewById(R.id.name)
            private val skilliq: TextView = itemView.findViewById(R.id.skill)
            private val country: TextView = itemView.findViewById(R.id.country)

            fun bind(skills: SkillDetails) {
                name.text = skills.name
                skilliq.text = skills.score.toString()
                country.text = skills.country
                Glide
                    .with(itemView.img)
                    .load(skills.badgeUrl)
                    .centerCrop()
                    .placeholder(R.drawable.skill)
                    .into(itemView.img)
            }
        }
    }



    // FUNCTIONS TO CREATE RETROFIT & DISPLAY DATA IN RECYCLERVIEW
    private fun getRetrofitSkills(){
        val retrofit =
            Retrofit.Builder()
                .baseUrl("https://gadsapi.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        //Step 2 Create Api service here to get the data from the API endpoint
        val api = retrofit.create(ApiService::class.java)
        api.getSkillIq().enqueue(object : Callback<List<SkillDetails>> {
            override fun onResponse(
                call: Call<List<SkillDetails>>, response: Response<List<SkillDetails>>
            ) {
                showSkillData(response.body()!!)
            }
            override fun onFailure(call: Call<List<SkillDetails>>, t: Throwable) {
                Toast.makeText(context, "ERROR LOADING DATA...", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
    private fun showSkillData(skill : List<SkillDetails>){
        recyclerView2.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SkillAdapter(skill)
        }
    }
}