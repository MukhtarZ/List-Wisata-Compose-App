package com.mukhtarz.listwisata.data.remote.model.list.user_login


import com.google.gson.annotations.SerializedName

data class AppMetadata(
    @SerializedName("provider")
    val provider: String,
    @SerializedName("providers")
    val providers: List<String>
)