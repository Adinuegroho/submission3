package com.example.submission3aplikasigithubuser.ui.detail.following

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3aplikasigithubuser.databinding.ItemFollowingBinding
import com.example.submission3aplikasigithubuser.model.FollowResponseItem
import com.example.submission3aplikasigithubuser.model.ModelUser
import com.example.submission3aplikasigithubuser.ui.detail.DetailActivity

class FollowingAdapter(private val listFollowing: List<FollowResponseItem>): RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    class ViewHolder (var binding: ItemFollowingBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFollowingBinding.inflate(LayoutInflater.from(parent.context),parent , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avatar = listFollowing[position].avatarUrl
        val login = listFollowing[position].login

        Glide.with(holder.itemView.context)
            .load(avatar)
            .into(holder.binding.imgItemUser)
        holder.binding.tvLogin.text = login

        holder.itemView.setOnClickListener {
            val intentToDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            val modelUser = ModelUser(login)
            intentToDetail.putExtra(DetailActivity.EXTRA_DATA, modelUser)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }

    override fun getItemCount(): Int = listFollowing.size
}