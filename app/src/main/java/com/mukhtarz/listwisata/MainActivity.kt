package com.mukhtarz.listwisata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.ListWisataTheme
import com.mukhtarz.listwisata.ui.screens.screenlogin.ScreenLogin
import com.mukhtarz.listwisata.ui.screens.screen_login_and_register.ScreenRegister
import com.mukhtarz.listwisata.ui.screens.screenwisata.WisataList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListWisataTheme {
                Surface {
                    val navController: NavHostController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.List.route
                    ) {
                        composable(Screen.Login.route){
                            ScreenLogin()
                        }
                        composable(Screen.List.route){
                            WisataList()
                        }
                        composable(Screen.Register.route){
                            ScreenRegister()
                        }
                    }
                }

            }
        }
    }
}
