package com.example.submission3aplikasigithubuser.ui.detail.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3aplikasigithubuser.api.ApiConfig
import com.example.submission3aplikasigithubuser.model.FollowResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {
    private val _items = MutableLiveData<List<FollowResponseItem>>()
    val items: LiveData<List<FollowResponseItem>> = _items

    fun findFollower(login: String) {
        val client = ApiConfig.getApiService().getFollow(login, TAG)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _items.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}" )
            }

        })
    }

    companion object {
        const val TAG = "followers"
    }
}