package com.example.submission3aplikasigithubuser.api

import com.example.submission3aplikasigithubuser.model.DetailUserResponse
import com.example.submission3aplikasigithubuser.model.FollowResponseItem
import com.example.submission3aplikasigithubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users?q=")
    fun getUsers(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<DetailUserResponse>

    @GET("users/{login}/{tipe}")
    fun getFollow(
        @Path("login") login: String,
        @Path("tipe") tipe: String
    ): Call<List<FollowResponseItem>>

}