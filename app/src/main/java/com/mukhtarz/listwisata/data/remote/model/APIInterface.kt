package com.mukhtarz.listwisata.data.remote.model

import com.mukhtarz.listwisata.data.remote.model.list.user_login.UserLogin
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPI
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPIItem
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface APIInterface {
    @POST("/auth/v1/signup")
    suspend fun userSignup(
        @Body body: RequestBody
    ):UserLogin

    @POST("/auth/v1/token?grant_type=password")
    suspend fun userLogin(
        @Body body: RequestBody
    ): UserLogin

    @GET("/rest/v1/tbl_wisata")//menarik semua data artikel
    suspend fun getListWisataData (
        @Query("select") select: String = "*"
    ): WisataListAPI

    @GET("/rest/v1/users?select=username")//menarik nama usernamenya doang buat memberi tau pemilik artikel wisata tersebut
    suspend fun getUsernameWisata (
        @Query("id") id: String
    ): WisataListAPIItem

    @GET("/rest/v1/tbl_wisata") //menarik semua detail data list wisata punya seseorang
    suspend fun getDetailListWisata (
        @Query("select") select: String = "*",
        @Query("id") id: String
    ):WisataListAPI

    @GET("/rest/v1/users")
    suspend fun getAllUserAuth (
        @Query("select") select: String = "*",
        @Query("id") id: String
    )

    @POST("/rest/v1/tbl_wisata")
    suspend fun addNewArticle (
        @Body body: RequestBody
    ):WisataListAPI

    @PATCH("/rest/v1/tbl_wisata")
    suspend fun updateArticle (
        @Query("id") id: String,
        @Body body: RequestBody
    )

    @DELETE("/rest/v1/tbl_wisata")
    suspend fun deleteArticle (
        @Query("id") id: String
    )












}