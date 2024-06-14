package com.mukhtarz.listwisata.ui.screens.updatearticlescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mukhtarz.listwisata.KotprefStorage
import com.mukhtarz.listwisata.data.remote.model.list.wisata_list.WisataListAPI
import com.mukhtarz.listwisata.helper.viewModelFactory
import com.mukhtarz.listwisata.ui.WisataListApplication
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateArticleScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateArticleViewModel = viewModel(factory = viewModelFactory {
        UpdateArticleViewModel(
            WisataListApplication.appmodule.getRepository
        )
    } ),
    goBack: () -> Unit,
    idArticle: Int,
    imgUrl: String,
    namaWisata: String,
    lokasiWisata: String,
    keteranganWisata: String
) {

    var addArtikelLokasi by remember {
        mutableStateOf(lokasiWisata)
    }

    var addArtikelJudul by remember {
        mutableStateOf(namaWisata)
    }

    var addArtikelKeterangan by remember {
        mutableStateOf(keteranganWisata)
    }

    var addImage by remember {
        mutableStateOf(imgUrl)
    }


    val addArticleState = viewModel.updateArticle.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "Update Artikel")
            },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            goBack()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = addArtikelJudul,
                    onValueChange = {
                        addArtikelJudul = it
                    },
                    label = {
                        Text(text = "Nama Wisata")
                    },
                    placeholder = {
                        Text(text = "Masukkan Nama Wisata")
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = addImage,
                    onValueChange = {
                        addImage = it
                    },
                    label = {
                        Text(text = "Gambar Wisata")
                    },
                    placeholder = {
                        Text(text = "Masukkan URL Gambar Wisata")
                    },
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = addArtikelLokasi,
                    onValueChange = {
                        addArtikelLokasi = it
                    },
                    label = {
                        Text(text = "Lokasi Wisata")
                    },
                    placeholder = {
                        Text(text = "Masukkan Lokasi Wisata")
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = addArtikelKeterangan,
                    onValueChange = {
                        addArtikelKeterangan = it
                    },
                    label = {
                        Text(text = "Keterangan Wisata")
                    },
                    placeholder = {
                        Text(text = "Masukkan Keterangan Wisata")
                    }
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        viewModel.updaterticlesUser(
                            id = idArticle,
                            buildJsonObject {
                                put("nama_wisata", addArtikelJudul)
                                put("gambar_wisata", addImage)
                                put("lokasi_wisata", addArtikelLokasi)
                                put("keterangan_wisata", addArtikelKeterangan)
                            }
                        )
                    }
                ) {
                    Text(text = "Update", textAlign = TextAlign.Center)
                }

            }
        }
        addArticleState.value.DisplayResult(
            onLoading = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(60.dp))
                    }
                }
            },
            onSuccess = {
                goBack()
            },
            onError = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error $it", fontSize = 20.sp, color = Color.Red)
                    }
                }
            }
        )

    }
}