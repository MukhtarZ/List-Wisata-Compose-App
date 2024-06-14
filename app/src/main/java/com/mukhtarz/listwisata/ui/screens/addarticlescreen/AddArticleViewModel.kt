package com.mukhtarz.listwisata.ui.screens.addarticlescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPI
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

class AddArticleViewModel(private val repository: ListWisataRepository):ViewModel(),
    ViewModelProvider.Factory {
    private val _addArticleState = MutableStateFlow<ResponseState<WisataListAPI>>(ResponseState.Idle)

    val addArticle = _addArticleState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle

        )

    fun addArticlesUser (user: JsonObject) {
        viewModelScope.launch {
            _addArticleState.emitAll(
                repository.addNewArticle(
                    user = user.toString().toRequestBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }



}