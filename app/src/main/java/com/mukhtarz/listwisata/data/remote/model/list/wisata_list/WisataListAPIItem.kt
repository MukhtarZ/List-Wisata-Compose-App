package com.mukhtarz.listwisata.data.remote.model.list.wisata_list


import com.google.gson.annotations.SerializedName

data class WisataListAPIItem(
    @SerializedName("author_id")
    val authorId: Any?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("gambar_wisata")
    val gambarWisata: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("keterangan_wisata")
    val keteranganWisata: String?,
    @SerializedName("lokasi_wisata")
    val lokasiWisata: String?,
    @SerializedName("nama_wisata")
    val namaWisata: String?
)