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
import com.example.leaderboardapp.models.LearnerDetails
import kotlinx.android.synthetic.main.learner_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LearnersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    // COMPANION OBJECT CREATED. IT IS NEEDED TO LINK FRAGMENT TO ITS
    // MAIN ACTIVITY
    companion object { fun newInstance(): LearnersFragment { return LearnersFragment() }}

    //DEFAULT FRAGMENT METHODS TO OVERRIDE
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment with its respective xml file.
        return inflater.inflate(R.layout.fragment_learner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        getRetrofitLearners()
    }


    // RECYCLERVIEW ADAPTER FOR LEARNERSFRAGMENT
    class LearnerAdapter(private val learner : List<LearnerDetails> )
        : RecyclerView.Adapter<LearnerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.learner_item, parent, false)
            return ViewHolder(itemView = view)
        }

        override fun getItemCount(): Int = learner.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            return holder.bind(learner[position])
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val name: TextView = itemView.findViewById(R.id.name)
            private val hours: TextView = itemView.findViewById(R.id.hours)
            private val country: TextView = itemView.findViewById(R.id.country)

            fun bind(learner: LearnerDetails) {
                name.text = learner.name
                hours.text = learner.hours.toString()
                country.text = learner.country
                Glide
                    .with(itemView.img)
                    .load(learner.badgeUrl)
                    .centerCrop()
                    .placeholder(R.drawable.badge)
                    .into(itemView.img)
            }


        }

    }

    // FUNCTIONS TO CREATE RETROFIT & DISPLAY DATA IN RECYCLERVIEW
    private fun getRetrofitLearners(){
        val retrofit =
            Retrofit.Builder()
                .baseUrl("https://gadsapi.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        //Step 2 Create Api service here to get the data from the API endpoint
        val api = retrofit.create(ApiService::class.java)
        api.getLearner().enqueue(object : Callback<List<LearnerDetails>> {
            override fun onResponse(
                call: Call<List<LearnerDetails>>, response: Response<List<LearnerDetails>>
            ) {
                showLearnerData(response.body()!!)
            }
            override fun onFailure(call: Call<List<LearnerDetails>>, t: Throwable) {
                Toast.makeText(context, "ERROR LOADING DATA...", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
    private fun showLearnerData(learner : List<LearnerDetails>){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = LearnerAdapter(learner)
        }
    }



}

