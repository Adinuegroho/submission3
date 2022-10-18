package com.example.submission3aplikasigithubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3aplikasigithubuser.R
import com.example.submission3aplikasigithubuser.databinding.ActivityMainBinding
import com.example.submission3aplikasigithubuser.model.ItemsItem
import com.example.submission3aplikasigithubuser.model.ModelUser
import com.example.submission3aplikasigithubuser.ui.detail.DetailActivity
import com.example.submission3aplikasigithubuser.ui.favorite.FavoriteActivity
import com.example.submission3aplikasigithubuser.ui.setting.ModeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.main_title)

        mainViewModel.items.observe(this) { items ->
            showRecylerList(items)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.isEmpty.observe(this) {
            showText(it)
        }

        fabfavorite()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mode -> startActivity(Intent(this@MainActivity, ModeActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showText(isEmpty: Boolean) {
        binding.tvToolsSearch.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun showRecylerList(items: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val mainAdapter = MainAdapter(items)
        binding.rvUsers.adapter = mainAdapter
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ModelUser) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                val login = data.login
                val userData = ModelUser(login)
                intentToDetail.putExtra(DetailActivity.EXTRA_DATA, userData)
                startActivity(intentToDetail)
            }
        })
    }

    private fun fabfavorite() {
        binding.fabFav.setOnClickListener {
            val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }
}