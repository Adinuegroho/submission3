package com.example.submission3aplikasigithubuser.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3aplikasigithubuser.database.Favorite
import com.example.submission3aplikasigithubuser.databinding.ItemFavoriteBinding
import com.example.submission3aplikasigithubuser.helper.FavoriteDiffCallback
import com.example.submission3aplikasigithubuser.model.ModelUser
import com.example.submission3aplikasigithubuser.ui.detail.DetailActivity

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                tvLogin.text = favorite.login
                Glide.with(itemView.context)
                    .load(favorite.avatarUrl)
                    .into(imgItemUser)

                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    val login = favorite.login.toString()
                    val userData = ModelUser(login)
                    intent.putExtra(DetailActivity.EXTRA_FAVORITE, favorite)
                    intent.putExtra(DetailActivity.EXTRA_DATA, userData)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    private val listFavorite = ArrayList<Favorite>()
    fun setListFavorite(listFavorite: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        return holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }
}