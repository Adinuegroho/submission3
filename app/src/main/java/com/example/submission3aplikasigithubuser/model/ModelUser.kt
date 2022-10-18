package com.example.submission3aplikasigithubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelUser(
    var login: String
): Parcelable
