package com.example.leaderboardapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.leaderboardapp.R
import com.example.leaderboardapp.api.ApiService
import kotlinx.android.synthetic.main.activity_submit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SubmitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)


        //Submit button method to validate if text fields are blank.
        submitRepo.setOnClickListener {
            val firstName = fName.text.toString().trim()
            val lastName = lName.text.toString().trim()
            val emailAddress = email.text.toString().trim()
            val link = gitRepo.text.toString().trim()
            when {
                firstName.isBlank()  -> {
                    fName.error = "Enter first name"
                    fName.requestFocus()
                    return@setOnClickListener
                }
                lastName.isBlank()  -> {
                    lName.error = "Enter last name"
                    lName.requestFocus()
                    return@setOnClickListener
                }
                emailAddress.isBlank() -> {
                    email.error = "Enter Email"
                    email.requestFocus()
                    return@setOnClickListener
                }
                link.isBlank() -> {
                    gitRepo.error = "Enter link to your Github"
                    gitRepo.requestFocus()
                    return@setOnClickListener
                }

            }

            postRetrofitUser()

        }
    }

    // Retrofit method to POST data to a server
    private fun postRetrofitUser(){
        val firstName = fName.text.toString().trim()
        val lastName = lName.text.toString().trim()
        val emailAddress = email.text.toString().trim()
        val link = gitRepo.text.toString().trim()

        val retrofit =
                Retrofit.Builder()
                        .baseUrl("https://docs.google.com/forms/d/e/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

        //Step 2 Create Api service here to get the data from the API endpoint
        val api = retrofit.create(ApiService::class.java)
        api.submitUserData(emailAddress, firstName, lastName, link)
        .enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>)
                    { if(response.isSuccessful)
                      {Toast.makeText(this@SubmitActivity,
                      "SUBMITTED SUCCESSFULLY!", Toast.LENGTH_LONG)
                                .show() }  else {
                              Toast.makeText(this@SubmitActivity,
                              "ERROR SENDING DATA...", Toast.LENGTH_LONG)
                                 .show()
                              }

            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@SubmitActivity, "ERROR SENDING DATA...", Toast.LENGTH_LONG)
                        .show()
            }
        })
    }




}