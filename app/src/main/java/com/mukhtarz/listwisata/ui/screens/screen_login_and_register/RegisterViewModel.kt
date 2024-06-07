package com.mukhtarz.listwisata.ui.screens.screen_login_and_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukhtarz.listwisata.data.repository.ListWisataRepository
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: ListWisataRepository) : ViewModel() {
    private  val _registerstate = MutableStateFlow<ResponseState<WisataListAPI>>(ResponseState.Idle)

    val loginState =_registerstate.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun getData(){
        viewModelScope.launch {
            _registerstate.emitAll(repository.getAllDataWisata())
        }
    }
}