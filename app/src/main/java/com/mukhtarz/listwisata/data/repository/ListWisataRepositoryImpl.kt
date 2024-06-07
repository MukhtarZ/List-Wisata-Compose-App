package com.mukhtarz.listwisata.data.repository

import com.mukhtarz.listwisata.data.remote.model.APIInterface
import com.mukhtarz.listwisata.data.remote.model.list.user_login.User
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPI
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPIItem
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListWisataRepositoryImpl(private val getApi: APIInterface): ListWisataRepository {
    override fun getAllDataWisata(): Flow<ResponseState<WisataListAPI>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = getApi.getListWisataData()
            emit(ResponseState.Success(result))
        }catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getDataWisataDetail(id: Int): Flow<ResponseState<WisataListAPIItem>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = getApi.getDetailListWisata()
            emit(ResponseState.Success(result))
        }catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun userLogin(): Flow<ResponseState<User>> = flow{
//        emit(ResponseState.Loading)
//        try {
//            val result = getApi.userLogin()
//        }
    }

    override fun userSignUp(): Flow<ResponseState<User>> {
        TODO("Not yet implemented")
    }


}