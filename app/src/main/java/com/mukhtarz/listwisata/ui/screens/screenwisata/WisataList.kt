package com.mukhtarz.listwisata.ui.screens.screenwisata

import android.widget.Space
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mukhtarz.listwisata.R
import com.mukhtarz.listwisata.helper.viewModelFactory
import com.mukhtarz.listwisata.ui.WisataListApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WisataList(
    modifier: Modifier = Modifier,
    viewModel: WisataListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = viewModelFactory {
        WisataListViewModel(
            WisataListApplication.appmodule.getRepository
        )
    }),
) {

    val wisataListState = viewModel.loginState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(title = {
                Text(
                    modifier = modifier.padding(3.dp),
                    text = "List Wisata",
                )
            },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { innerPadding ->

        wisataListState.value.DisplayResult(
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

                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    items(it) {wisataList ->
                        Card(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )

                        ) {
                            Column(
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    bottom = 7.dp,
                                    start = 5.dp,
                                    end = 5.dp
                                )
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp),
                                    shape = RoundedCornerShape(12.dp)

                                )
                                {
//                                    AsyncImage(
//                                        modifier = Modifier.fillMaxSize(),
//                                        model = wisataList.gambarWisata,
//                                        contentScale = ContentScale.Crop,
//                                        contentDescription = "Gambar Wisata"
//                                    )
                                    Image(painter = painterResource(id = R.drawable.raja_ampat), contentDescription = "sa")
                                }
                            }
                            Spacer(modifier = Modifier.size(6.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 16.dp)
                            ) {
                                Text(text = "${wisataList.namaWisata}", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = "${wisataList.lokasiWisata}")
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                modifier = Modifier.padding(start = 6.dp, bottom = 12.dp),
                                text = "${wisataList.keteranganWisata}"
                            )
                        }
                    }
                }
            },
            onError = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error", fontSize = 20.sp, color = Color.Red)
                    }
                }
            }
        )
    }

}
