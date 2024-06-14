package com.mukhtarz.listwisata.ui.screens.detailscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mukhtarz.listwisata.R
import com.mukhtarz.listwisata.helper.viewModelFactory
import com.mukhtarz.listwisata.ui.WisataListApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = viewModelFactory {
        DetailViewModel(
            WisataListApplication.appmodule.getRepository
        )
    }),
    idArtikel: Int,
    goList: () -> Unit,
    updateArticle: (imgUrl: String, namaWisata: String, lokasi: String, deskripsi: String) -> Unit,
) {

    val detailState = viewModel.detailState.collectAsStateWithLifecycle()

    val deleteArticle = viewModel.deleteArticle.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getDataDetail(idArtikel)
    }

    var imgUrl by remember { mutableStateOf("") }

    var namaWisata by remember { mutableStateOf("") }

    var lokasi by remember { mutableStateOf("") }

    var deskripsi by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail") },
                navigationIcon = {
                    IconButton(onClick = { goList() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Ikon Kembali"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        goList()
                        viewModel.deleteArticle(idArticle = idArtikel)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Artikel"
                        )
                    }
                }
            )

        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                updateArticle(imgUrl, namaWisata, lokasi, deskripsi)
            }
            ) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "Update Artikel")
            }
        }
    ) { innerPadding ->
        detailState.value.DisplayResult(
            modifier = Modifier.padding(innerPadding),
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
            onSuccess = { productItem ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(productItem) {

                        imgUrl = it.gambarWisata!!
                        namaWisata = it.namaWisata!!
                        lokasi = it.lokasiWisata!!
                        deskripsi = it.keteranganWisata!!

                        Spacer(modifier = Modifier.size(16.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = it.gambarWisata,
                                contentDescription = "Gambar ${it.namaWisata}",
                                contentScale = ContentScale.Crop
                            )
//                            Image(modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop, painter = painterResource(id = R.drawable.raja_ampat), contentDescription = "asdpakwp")
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "${it.namaWisata}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.size(9.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Lokasi wisata ${it.namaWisata}"
                            )
                            Spacer(modifier = Modifier.size(7.dp))
                            Text(text = "${it.lokasiWisata}")
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            text = "${it.keteranganWisata}",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

            },
            onError = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error!! $it",
                            fontSize = 22.sp,
                            color = Color.Red
                        )
                    }
                }
            }
        )
        deleteArticle.value.DisplayResult(
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
                Toast.makeText(context, "Note Berhasil Di Hapus", Toast.LENGTH_SHORT).show()
                goList()
            },
            onError = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error!! $it",
                            fontSize = 22.sp,
                            color = Color.Red
                        )
                    }
                }
            }
        )

    }
}