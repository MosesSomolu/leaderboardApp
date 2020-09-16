package com.example.leaderboardapp.api

import com.example.leaderboardapp.models.LearnerDetails
import com.example.leaderboardapp.models.SkillDetails
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

// Step 2 Set up API service
interface ApiService {
    @GET("/api/hours")
    fun getLearner() : Call<List<LearnerDetails>>
    @GET("/api/skilliq")
    fun getSkillIq() : Call<List<SkillDetails>>

    @FormUrlEncoded
    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    fun submitUserData(
        @Field("entry.1824927963") email: String,
        @Field("entry.1877115667") firstName: String,
        @Field("entry.2006916086") lastName: String,
        @Field("entry.284483984") linkToProject: String
    ): Call<Void>
}