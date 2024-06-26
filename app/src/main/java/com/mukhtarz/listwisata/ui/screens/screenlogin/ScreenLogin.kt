package com.mukhtarz.listwisata.ui.screens.screenlogin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mukhtarz.listwisata.KotprefStorage
import com.mukhtarz.listwisata.R
import com.mukhtarz.listwisata.helper.viewModelFactory
import com.mukhtarz.listwisata.ui.WisataListApplication
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLogin(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = viewModelFactory {
        LoginViewModel(
            WisataListApplication.appmodule.getRepository
        )
    }),
    goSignUp: () -> Unit,
    goToList: () -> Unit,
) {

    val loginState = viewModel.loginState.collectAsStateWithLifecycle()

    var username by remember {
        mutableStateOf("")
    }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Box(modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Login",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(50.dp))
                TextField(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    value = username,
                    onValueChange = { newText ->
                        username = newText
                    },
                    label = {
                        Text(text = "Email")
                    },
                    placeholder = {
                        Text(text = "Masukkan Email")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.size(7.dp))
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = { Text("Password") },
                    singleLine = true,
                    placeholder = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            painterResource(id = R.drawable.baseline_visibility_24)
                        else painterResource(id = R.drawable.baseline_visibility_off_24)


                        // Please provide localized description for accessibility services
                        val description =
                            if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, description)
                        }
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = "Belum Punya Akun ?",
                        fontSize = 13.sp
                    )
                    TextButton(
                        onClick = { goSignUp() },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "Register", fontSize = 13.sp)
                    }
                }
                Button(
                    onClick = {
                        viewModel.postUserLogin(
                            buildJsonObject {
                                put("email", username)
                                put("password", password)
                            }
                        )

                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(text = "Login")
                }

            }
        }
        loginState.value.DisplayResult(
            onLoading = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                        CircularProgressIndicator(modifier.size(60.dp))
                    }
                }
            },
            onSuccess = {
                goToList()
                KotprefStorage.email = it.user.email
                KotprefStorage.accessToken = it.accessToken
                KotprefStorage.username = it.user.userMetadata.username
                KotprefStorage.userUid = it.user.id
            },
            onError = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error!! \n $it", fontSize = 20.sp, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        )
    }


}




