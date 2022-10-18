package com.example.submission3aplikasigithubuser.ui.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submission3aplikasigithubuser.R
import com.example.submission3aplikasigithubuser.databinding.ActivityModeBinding
import com.example.submission3aplikasigithubuser.helper.SettingPreferences
import com.example.submission3aplikasigithubuser.helper.ViewModelFactory

val Context.dataStrore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class ModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.setting)

        val modeViewModel = obtainViewModel(this)
        modeViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _:CompoundButton?, isChecked: Boolean ->
            modeViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ModeViewModel {
        val pref = SettingPreferences.getInstance(dataStrore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory)[ModeViewModel::class.java]
    }
}