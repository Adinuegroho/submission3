package com.example.submission3aplikasigithubuser.ui.detail.followers

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3aplikasigithubuser.databinding.ItemUserBinding
import com.example.submission3aplikasigithubuser.model.FollowResponseItem
import com.example.submission3aplikasigithubuser.model.ModelUser
import com.example.submission3aplikasigithubuser.ui.detail.DetailActivity

class FollowerAdapter(private val listFollow: List<FollowResponseItem>): RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    class ViewHolder (var binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avatar = listFollow[position].avatarUrl
        val login = listFollow[position].login

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

    override fun getItemCount(): Int = listFollow.size
}