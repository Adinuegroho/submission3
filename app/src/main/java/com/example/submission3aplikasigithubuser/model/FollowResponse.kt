package com.example.submission3aplikasigithubuser.model

import com.google.gson.annotations.SerializedName

data class FollowResponseItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,
)
