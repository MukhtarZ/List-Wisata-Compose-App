package com.mukhtarz.listwisata.ui.screens.profileuser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mukhtarz.listwisata.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilUserScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
) {
    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profil User")
                },
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }) { innerPadding ->
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(innerPadding)) 
        {
            Card(modifier.size(120.dp), shape = RoundedCornerShape(60.dp)) {
                Image(modifier = Modifier.fillMaxSize(),painter = painterResource(id = R.drawable.raja_ampat), contentDescription = "gambar profil user")
            }


        }
    }
}