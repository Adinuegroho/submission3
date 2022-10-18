package com.example.submission3aplikasigithubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3aplikasigithubuser.databinding.ItemUserBinding
import com.example.submission3aplikasigithubuser.model.ItemsItem
import com.example.submission3aplikasigithubuser.model.ModelUser

class MainAdapter(private val listUser: List<ItemsItem>): RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: ModelUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avatar = listUser[position].avatarUrl
        val login = listUser[position].login

        Glide.with(holder.itemView.context)
            .load(avatar)
            .into(holder.binding.imgItemUser)
        holder.binding.tvLogin.text = login

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(ModelUser(login))
        }
    }

    override fun getItemCount(): Int = listUser.size
}