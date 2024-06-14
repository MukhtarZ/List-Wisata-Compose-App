package com.mukhtarz.listwisata.ui.screens.screen_register

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun ScreenRegister(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(factory = viewModelFactory {
        RegisterViewModel(
            WisataListApplication.appmodule.getRepository
        )
    }),
    goToList: () -> Unit,
    goToLogin: ()-> Unit
) {
    var textFieldValueUsername by remember {
        mutableStateOf("")
    }
    var textFieldValueEmail by remember {
        mutableStateOf("")
    }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val registerState = viewModel.registerState.collectAsStateWithLifecycle()

    Scaffold() { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Register",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(50.dp))
                TextField(
                    value = textFieldValueUsername,
                    onValueChange = {
                        textFieldValueUsername = it
                    },
                    label = {
                        Text(text = "Username")
                    },
                    placeholder = {
                        Text(text = "Masukkan Username")
                    }
                )
                Spacer(modifier = Modifier.size(5.dp))
                TextField(
                    value = textFieldValueEmail,
                    onValueChange = {
                        textFieldValueEmail = it
                    },
                    label = {
                        Text(text = "Email")
                    },
                    placeholder = {
                        Text(text = "Masukkan Email")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.size(5.dp))
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
                            painterResource(id = R.drawable.baseline_visibility_off_24)
                        else painterResource(id = R.drawable.baseline_visibility_24)

                        // Please provide localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"

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
                        text = "Sudah Punya Akun ?",
                        fontSize = 13.sp
                    )
                    TextButton(onClick = { goToLogin() }, contentPadding = PaddingValues(0.dp)) {
                        Text(text = "Login", fontSize = 13.sp)
                    }
                }
                Button(
                    onClick = {
                        viewModel.postSignUp(
                            buildJsonObject {
                                put("email", textFieldValueEmail)
                                put("password", password)
                                put("data", buildJsonObject {
                                    put("username", textFieldValueUsername)
                                })
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(text = "Register")
                }
            }
        }
        registerState.value.DisplayResult(
            onLoading = {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
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
                goToList()
                KotprefStorage.username = it.user.userMetadata.username
                KotprefStorage.email = it.user.userMetadata.email
                KotprefStorage.accessToken = it.accessToken
                KotprefStorage.userUid = it.user.id
            },
            onError = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = it, fontSize = 20.sp, color = Color.Red)
                    }
                }
            })
    }
}