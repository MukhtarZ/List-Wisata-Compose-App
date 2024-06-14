package com.mukhtarz.listwisata.ui.screens.profileadmin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mukhtarz.listwisata.KotprefStorage

@Preview
@Composable
fun PreviewProfilAdminScreen(modifier: Modifier = Modifier) {
    ProfilAdminScreen(goBack = {}, goToSignUp = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilAdminScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    goToSignUp: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profil")
                },
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali Ke List Wisata"
                        )
                    }
                })
        }
    ) { innerPadding ->

        Column(
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(200.dp),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "profil"
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(text = KotprefStorage.username, style = typography.titleLarge)
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Email :")
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = KotprefStorage.email, style = typography.titleLarge)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                onClick = {
                    goToSignUp()
                    KotprefStorage.clear()
                }
            ) {
                Text(text = "Log Out")
            }


        }
    }
}





