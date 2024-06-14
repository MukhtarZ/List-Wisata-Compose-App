package com.mukhtarz.listwisata.data.repository

import com.mukhtarz.listwisata.data.remote.model.list.user_login.UserLogin
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPI
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody


interface ListWisataRepository {
    fun getAllDataWisata(): Flow<ResponseState<WisataListAPI>>

    fun getDataWisataDetail(id: String): Flow<ResponseState<WisataListAPI>>

    fun userLogin(user: RequestBody): Flow<ResponseState<UserLogin>>

    fun userSignUp(user: RequestBody): Flow<ResponseState<UserLogin>>

    fun deleteArticle(idArticle: String): Flow<ResponseState<Unit?>>

    fun addNewArticle(user: RequestBody): Flow<ResponseState<WisataListAPI>>

    fun updateArticle(id: String, user: RequestBody): Flow<ResponseState<Boolean>>

}