package com.mukhtarz.listwisata.data.remote.model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import com.mukhtarz.listwisata.data.remote.model.list.user_login.User
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPI
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPIItem
import retrofit2.http.DELETE

interface APIInterface {
    @POST("/auth/v1/signup")
    suspend fun userSignup(
        @Body body: Map<String,Any>
    ):User

    @POST("/auth/v1/token?grant_type=password")
    suspend fun userLogin(
        @Body body: Map<String, Any>
    ): User

    @GET("/rest/v1/tbl_wisata?select=*")//menarik semua data artikel
    suspend fun getListWisataData (
    ): WisataListAPI

    @GET("/rest/v1/users?select=username")//menarik nama usernamenya doang buat memberi tau pemilik artikel wisata tersebut
    suspend fun getUsernameWisata (
        @Query("id") id: String
    ): WisataListAPIItem

    @GET("/rest/v1/tbl_wisata?select=*") //menarik semua detail data list wisata punya seseorang
    suspend fun getDetailListWisata (
        @Query("author_id") authorId: String = "author_id"
    ):WisataListAPIItem

    @POST("/rest/v1/tbl_wisata")
    suspend fun addNewArticle (
        @Body body: Map<String, Any>
    ):WisataListAPIItem

    @POST("/rest/v1/tbl_wisata")
    suspend fun updateArticle (
        @Query("id") id: String,
        @Body body: Map<String, Any>
    ):WisataListAPIItem

    @DELETE("/rest/v1/tbl_wisata")
    suspend fun deleteArticle (
        @Query("id") id: String
    )










}