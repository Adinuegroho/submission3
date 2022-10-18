package com.example.submission3aplikasigithubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3aplikasigithubuser.R
import com.example.submission3aplikasigithubuser.databinding.ActivityFavoriteBinding
import com.example.submission3aplikasigithubuser.helper.SettingPreferences
import com.example.submission3aplikasigithubuser.helper.ViewModelFactory
import com.example.submission3aplikasigithubuser.ui.setting.dataStrore

class FavoriteActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityMainBinding

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = resources.getString(R.string.favorite)

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorite().observe(this) {favoriteList ->
            if (favoriteList != null) {
                adapter.setListFavorite(favoriteList)
            }
        }

        adapter = FavoriteAdapter()
        binding?.rvFav?.layoutManager = LinearLayoutManager(this)
        binding?.rvFav?.setHasFixedSize(true)
        binding?.rvFav?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val pref = SettingPreferences.getInstance(dataStrore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

}