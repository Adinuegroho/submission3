package com.example.submission3aplikasigithubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.submission3aplikasigithubuser.database.Favorite

class FavoriteDiffCallback(private val mOldFavoriteList: List<Favorite>, private val mNewFavoriteList: List<Favorite>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = mOldFavoriteList[oldItemPosition]
        val newFavorite = mNewFavoriteList[newItemPosition]
        return oldFavorite.login == newFavorite.login && oldFavorite.avatarUrl == newFavorite.avatarUrl
    }

}