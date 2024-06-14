package com.mukhtarz.listwisata.data.remote.model.list.userauth


import com.google.gson.annotations.SerializedName

data class UsersItem(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_admin")
    val isAdmin: Boolean,
    @SerializedName("username")
    val username: String
)