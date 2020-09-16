package com.example.leaderboardapp.models

import com.google.gson.annotations.SerializedName

// Step 3 set data model to receive data
// Data models to store data received from API endpoints

data class LearnerDetails(
    var name : String,
    var hours : Int,
    var country : String,
    var badgeUrl : String
)

data class SkillDetails(
    var name : String,
    var score : Int,
    var country : String,
    var badgeUrl : String
)

data class SubmissionResponse(
    @SerializedName("firstName") var firstName : String,
    @SerializedName("lastName") var lastName : String,
    @SerializedName("email") var email : String,
    @SerializedName("linkToProject") var linkToProject : String
)

