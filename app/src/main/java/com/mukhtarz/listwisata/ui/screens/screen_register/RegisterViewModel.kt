package com.mukhtarz.listwisata.ui.screens.screen_register

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

class RegisterViewModel(private val repository: ListWisataRepository) : ViewModel() {
    private val _registerstate = MutableStateFlow<ResponseState<UserLogin>>(ResponseState.Idle)

    val registerState = _registerstate.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle

        )

    fun postSignUp(user: JsonObject) {
        viewModelScope.launch {
            _registerstate.emitAll(
              flow = repository.userSignUp(
                    user = user
                        .toString()
                        .toRequestBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }

}