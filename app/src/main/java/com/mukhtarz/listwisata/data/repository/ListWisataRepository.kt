package com.mukhtarz.listwisata.data.repository

import com.mukhtarz.listwisata.data.remote.model.list.user_login.User
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPI
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPIItem
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.Flow


interface ListWisataRepository {
    fun getAllDataWisata(): Flow<ResponseState<WisataListAPI>>

    fun getDataWisataDetail(id: Int): Flow<ResponseState<WisataListAPIItem>>

    fun userLogin(): Flow<ResponseState<User>>

    fun userSignUp(): Flow<ResponseState<User>>


}