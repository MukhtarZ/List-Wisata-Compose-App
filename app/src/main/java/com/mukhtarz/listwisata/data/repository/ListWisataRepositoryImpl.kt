package com.mukhtarz.listwisata.data.repository

import com.mukhtarz.listwisata.data.remote.model.APIInterface
import com.mukhtarz.listwisata.data.remote.model.list.user_login.UserLogin
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPI
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class ListWisataRepositoryImpl(private val getApi: APIInterface) : ListWisataRepository {
    override fun getAllDataWisata(): Flow<ResponseState<WisataListAPI>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = getApi.getListWisataData()
            emit(ResponseState.Success(result))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getDataWisataDetail(id: String): Flow<ResponseState<WisataListAPI>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = getApi.getDetailListWisata(id = id)
            emit(ResponseState.Success(result))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun userLogin(user: RequestBody): Flow<ResponseState<UserLogin>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = getApi.userLogin(user)
            emit(ResponseState.Success(result))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun userSignUp(user: RequestBody): Flow<ResponseState<UserLogin>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = getApi.userSignup(user)
            emit(ResponseState.Success(result))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun deleteArticle(idArticle: String): Flow<ResponseState<Unit?>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = getApi.deleteArticle(idArticle)
            emit(ResponseState.Success(result))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }


    override fun addNewArticle(user: RequestBody): Flow<ResponseState<WisataListAPI>> = flow {
        emit(ResponseState.Idle)
        try {
            val result = getApi.addNewArticle(user)
            emit(ResponseState.Success(result))
        }catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun updateArticle(id: String, user: RequestBody): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Idle)
        try {
            val result = getApi.updateArticle(id = id, body = user)
            emit(ResponseState.Success(true))
        }catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
}
