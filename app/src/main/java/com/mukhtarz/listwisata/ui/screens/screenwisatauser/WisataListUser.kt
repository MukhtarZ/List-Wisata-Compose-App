package com.mukhtarz.listwisata.ui.screens.screenwisatauser

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mukhtarz.listwisata.R
import com.mukhtarz.listwisata.data.remote.model.list.ViewModelRefresh
import com.mukhtarz.listwisata.helper.viewModelFactory
import com.mukhtarz.listwisata.ui.WisataListApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WisataListUser(
    modifier: Modifier = Modifier,
    viewModel: WisataListUserViewModel = viewModel(factory = viewModelFactory {
        WisataListUserViewModel(
            WisataListApplication.appmodule.getRepository
        )
    }),
    goToProfile: () -> Unit,
    goToDetail: (idArtikel: Int) -> Unit,
    goToAddArticle: () -> Unit
) {

    val viewModelRefresh = viewModel<ViewModelRefresh>()
    val isLoading by viewModelRefresh.isLoading.collectAsState()
    val refreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    val wisataListState = viewModel.wisataState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getData()
    }


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
                },
                actions = {
                    IconButton(onClick = { goToProfile() }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    goToAddArticle()
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "menambah artikel")
            }
        }
    ) { innerPadding ->
        SwipeRefresh(
            state = refreshState,
            onRefresh = { viewModelRefresh.loadStuff() },
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    backgroundColor = Color.Cyan,
                    contentColor = Color.Black
                )
            }
        ) {
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
                        items(it) { wisataList ->
                            Card(
                                onClick = { goToDetail(wisataList.id!!) },
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
                                        AsyncImage(
                                            modifier = Modifier.fillMaxSize(),
                                            model = wisataList.gambarWisata,
                                            contentScale = ContentScale.Crop,
                                            contentDescription = "Gambar Wisata"
                                        )
//                                    Image(modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop, painter = painterResource(id = R.drawable.raja_ampat), contentDescription = "sa")
                                    }
                                }
                                Spacer(modifier = Modifier.size(6.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp)
                                ) {
                                    Text(
                                        text = wisataList.namaWisata!!,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(text = wisataList.lokasiWisata!!)
                                }
                                Spacer(modifier = Modifier.size(16.dp))
                                Text(
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        bottom = 12.dp,
                                        end = 16.dp
                                    ),
                                    text = wisataList.keteranganWisata!!,
                                    maxLines = 5,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                },
                onError = {
                    Log.d("ERROR", it)
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

}



