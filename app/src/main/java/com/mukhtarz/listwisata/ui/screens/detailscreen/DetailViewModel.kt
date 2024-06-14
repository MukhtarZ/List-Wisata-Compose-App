package com.mukhtarz.listwisata.ui.screens.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPI
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPIItem
import com.mukhtarz.listwisata.data.repository.ListWisataRepository
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: ListWisataRepository) : ViewModel() {
    private val _detailListState =
        MutableStateFlow<ResponseState<WisataListAPI>>(ResponseState.Idle)

    val detailState = _detailListState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun getData() {
        viewModelScope.launch {
            _detailListState.emitAll(repository.getAllDataWisata())
        }
    }

    fun getDataDetail(idArticle: Int){
        viewModelScope.launch {
            _detailListState.emitAll(
                repository.getDataWisataDetail(
                    id = "eq.$idArticle"
                )
            )
        }
    }

    private val _deleteArticle =  MutableStateFlow<ResponseState<Unit?>>(ResponseState.Idle)

    val deleteArticle = _deleteArticle.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )
    fun deleteArticle(idArticle: Int){
        viewModelScope.launch {
            _deleteArticle.emitAll(
                repository.deleteArticle(idArticle = "eq.$idArticle")
            )
        }
    }
}