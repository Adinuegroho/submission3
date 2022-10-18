package com.example.submission3aplikasigithubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission3aplikasigithubuser.R
import com.example.submission3aplikasigithubuser.database.Favorite
import com.example.submission3aplikasigithubuser.databinding.ActivityDetailBinding
import com.example.submission3aplikasigithubuser.helper.SettingPreferences
import com.example.submission3aplikasigithubuser.helper.ViewModelFactory
import com.example.submission3aplikasigithubuser.model.DetailUserResponse
import com.example.submission3aplikasigithubuser.model.ModelUser
import com.example.submission3aplikasigithubuser.ui.setting.ModeViewModel
import com.example.submission3aplikasigithubuser.ui.setting.dataStrore
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding
    private var favorite: Favorite? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val dataDetailUser = intent.getParcelableExtra<ModelUser>(EXTRA_DATA) as ModelUser
        val login = dataDetailUser.login
        sectionAdapter()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = resources.getString(R.string.detail_title)

        favorite = intent.getParcelableExtra(EXTRA_FAVORITE)

        if (favorite == null) {
            favorite = Favorite()
        }
        binding?.fabStatusFav?.setOnClickListener {
            detailViewModel.data.observe(this) { data ->
                if (favorite?.login == data.login) {
                    deleteData()
                } else {
                    saveData(data)
                }
            }
        }

        detailViewModel = obtainViewModel(this@DetailActivity)
        detailViewModel.findUser(login)
        detailViewModel.data.observe(this) { data ->
            setDetailUser(data)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val modeModeViewModel = obtainModeViewModel(this)
        modeModeViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            setDarkMode(isDarkModeActive)
        }
    }

    private fun setDarkMode(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            binding?.apply {
                imgPerson.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_person_dark))
                imgLockPerson.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_lock_person_dark))
                imgLocation.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_location_dark))
                imgCompany.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_corporate_dark))
            }
        } else {
            binding?.apply {
                imgPerson.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_person))
                imgLockPerson.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_lock_person))
                imgLocation.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_location))
                imgCompany.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_corporate))
            }
        }
    }

    private fun deleteData() {
       favorite.let { favorite ->
           favorite?.login = null
           favorite?.avatarUrl = null
           detailViewModel.delete(favorite as Favorite)
           showToast(resources.getString(R.string.delete))
           setStatusFav(false)
       }
    }

    private fun saveData(detailUserResponse: DetailUserResponse) {
//        detailViewModel.getAllFavorite().observe(this) { favoriteList ->
//            for (user in favoriteList) {
//                if (user.login != detailUserResponse.login) {
//                }
//                break
//            }
//        }
        favorite.let { favorite ->
            favorite?.login = detailUserResponse.login
            favorite?.avatarUrl = detailUserResponse.avatarUrl
        }
        detailViewModel.insert(favorite as Favorite)
        showToast(resources.getString(R.string.add))
        setStatusFav(true)
    }

    private fun sectionAdapter() {
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding!!.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding!!.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setDetailUser(detailUser: DetailUserResponse) {
        detailViewModel.getAllFavorite().observe(this) { favoriteList ->
            for (user in favoriteList) {
                if (user.login == detailUser.login) {
                    setStatusFav(true)
                }
            }
        }
        val dataCompany = detailUser.company
        val dataLocation = detailUser.location
        binding?.apply {
            tvDataPerson.text = detailUser.name
            tvDataLockPerson.text = detailUser.login
            tvDataLocation.text = dataLocation
            tvDataCompany.text = dataCompany
            tvDataRepo.text = detailUser.publicRepos.toString()
            tvDataFollowing.text = detailUser.following.toString()
            tvDataFollower.text = detailUser.followers.toString()
            Glide.with(this@DetailActivity)
                .load(detailUser.avatarUrl)
                .into(binding!!.imgDataUser)

            imgLocation.visibility = if (dataLocation.isNullOrEmpty()) View.GONE else View.VISIBLE
            imgCompany.visibility = if (dataCompany.isNullOrEmpty()) View.GONE else View.VISIBLE

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val pref = SettingPreferences.getInstance(dataStrore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun obtainModeViewModel(activity: AppCompatActivity): ModeViewModel {
        val pref = SettingPreferences.getInstance(dataStrore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory)[ModeViewModel::class.java]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setStatusFav(statusFav: Boolean) {
        if (statusFav) {
            binding?.fabStatusFav?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        } else {
            binding?.fabStatusFav?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.content_tab_following,
            R.string.content_tab_follower
        )
        const val EXTRA_DATA = "Extra_Data"
        const val EXTRA_FAVORITE = "Extra_Favorite"
    }
}