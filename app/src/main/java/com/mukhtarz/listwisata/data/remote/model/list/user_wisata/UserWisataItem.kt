package com.mukhtarz.listwisata.data.remote.model.list.user_wisata


import com.google.gson.annotations.SerializedName

data class UserWisataItem(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("is_admin")
    val isAdmin: Boolean?,
    @SerializedName("username")
    val username: String?
)