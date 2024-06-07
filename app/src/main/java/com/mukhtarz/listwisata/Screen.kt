package com.mukhtarz.listwisata

sealed class Screen(val route: String) {
    data object Login : Screen ("screen_login")

    data object Register: Screen ("screen_register")

    data object List: Screen ("list_wisata")
}