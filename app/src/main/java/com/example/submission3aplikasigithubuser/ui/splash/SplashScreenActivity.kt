package com.example.submission3aplikasigithubuser.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.submission3aplikasigithubuser.R
import com.example.submission3aplikasigithubuser.helper.SettingPreferences
import com.example.submission3aplikasigithubuser.helper.ViewModelFactory
import com.example.submission3aplikasigithubuser.ui.main.MainActivity
import com.example.submission3aplikasigithubuser.ui.setting.ModeViewModel
import com.example.submission3aplikasigithubuser.ui.setting.dataStrore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)
    private val delay: Long = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        splashScreen(delay)

        val modeViewModel = obtainViewModel(this)
        modeViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ModeViewModel {
        val pref = SettingPreferences.getInstance(dataStrore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory).get(ModeViewModel::class.java)
    }

    private fun splashScreen(delay: Long) {
        activityScope.launch {
            delay(delay)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }
    }
}