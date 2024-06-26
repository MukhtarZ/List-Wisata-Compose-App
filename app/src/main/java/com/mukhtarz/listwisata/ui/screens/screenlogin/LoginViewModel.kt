package com.mukhtarz.listwisata.ui.screens.screenlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukhtarz.listwisata.data.remote.model.list.user_login.UserLogin
import com.mukhtarz.listwisata.data.repository.ListWisataRepository
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LoginViewModel(private val repository: ListWisataRepository) : ViewModel() {
    private val _loginstate = MutableStateFlow<ResponseState<UserLogin>>(ResponseState.Idle)

    val loginState = _loginstate.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )
    fun postUserLogin(users: JsonObject) {
        viewModelScope.launch {
            _loginstate.emitAll(
                repository.userLogin(
                    user = users.toString().toRequestBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }

}