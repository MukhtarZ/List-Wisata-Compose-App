package com.mukhtarz.listwisata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chibatching.kotpref.Kotpref
import com.example.compose.ListWisataTheme
import com.mukhtarz.listwisata.ui.screens.addarticlescreen.AddArticleScreen
import com.mukhtarz.listwisata.ui.screens.detailscreen.DetailScreen
import com.mukhtarz.listwisata.ui.screens.profileadmin.ProfilAdminScreen
import com.mukhtarz.listwisata.ui.screens.screen_register.ScreenRegister
import com.mukhtarz.listwisata.ui.screens.screenlogin.ScreenLogin
import com.mukhtarz.listwisata.ui.screens.screenwisataadmin.WisataListAdmin
import com.mukhtarz.listwisata.ui.screens.screenwisatauser.WisataListUser
import com.mukhtarz.listwisata.ui.screens.updatearticlescreen.UpdateArticleScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Kotpref.init(this)
        setContent {
            ListWisataTheme {
                Surface {
                    var idUpdate by remember {
                        mutableStateOf(0)
                    }
                    val navController: NavHostController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.List.route
                    ) {
                        composable(Screen.Login.route) {
                            ScreenLogin(
                                goSignUp = {
                                    navController.navigate(Screen.Register.route)
                                },
                                goToList = {
                                    navController.navigate(Screen.List.route)
                                }
                            )
                        }
                        composable(Screen.List.route) {
                            WisataListUser(
                                goToProfile = {
                                    navController.navigate(Screen.ProfileAdmin.route)
                                },
                                goToDetail = {
                                    navController.navigate(Screen.DetailList.createRoute(idArtikel = it))
                                },
                                goToAddArticle = {
                                    navController.navigate(Screen.AddArtikel.route)
                                }

                            )
                        }
                        composable(Screen.Register.route) {
                            ScreenRegister(
                                goToList = {
                                    navController.navigate(Screen.List.route)
                                },
                                goToLogin = {
                                    navController.navigate(Screen.Login.route)
                                }
                            )
                        }
//                        composable(Screen.ListAdmin.route) {
//                            WisataListAdmin()
//                        }
                        composable(Screen.DetailList.route, arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("id") ?: 0
                            idUpdate = id
                            DetailScreen(
                                idArtikel = id,
                                goList = {
                                    navController.navigateUp()
                                },
                                updateArticle = { img_url, nama_wisata, lokasi, deskripsi ->
                                    navController.navigate(Screen.UpdateArtikel.createRoute(img_url = img_url, nama_wisata = nama_wisata, lokasi = lokasi, deskripsi = deskripsi))
                                }
                            )

                        }
                        composable(Screen.UpdateArtikel.route, arguments = listOf(
                            navArgument("img_url"){
                                type = NavType.StringType
                            },
                            navArgument("nama_wisata"){
                                type = NavType.StringType
                            },
                            navArgument("lokasi"){
                                type = NavType.StringType
                            },
                            navArgument("deskripsi"){
                                type = NavType.StringType
                            }
                        )) {
                            val imgUrl = it.arguments?.getString("img_url") ?: ""
                            val namaWisata = it.arguments?.getString("nama_wisata") ?: ""
                            val lokasi = it.arguments?.getString("lokasi") ?: ""
                            val deskripsi = it.arguments?.getString("deskripsi") ?: ""
                            UpdateArticleScreen(
                                goBack = {
                                    navController.navigateUp()
                                },
                                idArticle = idUpdate,
                                namaWisata = namaWisata,
                                imgUrl = imgUrl,
                                lokasiWisata = lokasi,
                                keteranganWisata = deskripsi
                            )
                        }
                        composable(Screen.ProfileAdmin.route) {
                            ProfilAdminScreen(
                                goBack = {
                                    navController.navigateUp()
                                },
                                goToSignUp = {
                                    navController.navigate(Screen.Register.route) {
                                        popUpTo(
                                            if (KotprefStorage.accessToken == "") {
                                                Screen.Register.route
                                            } else {
                                                Screen.List.route
                                            }
                                        ) {
                                            inclusive = true
                                            saveState = false
                                        }
                                    }
                                }
                            )
                        }
                        composable(Screen.AddArtikel.route) {
                            AddArticleScreen(
                                goBack = {
                                    navController.navigateUp()
                                }
                            )
                        }

                    }
                }

            }
        }
    }
}
