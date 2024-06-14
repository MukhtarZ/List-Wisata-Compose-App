package com.mukhtarz.listwisata

sealed class Screen(val route: String) {
    data object Login : Screen ("screen_login")

    data object Register: Screen ("screen_register")

    data object List: Screen ("list_wisata")

    data object ProfileAdmin: Screen ("screen_profile")

//    data object ListAdmin: Screen("list_admin_wisata")

    data object DetailList: Screen("detail_screen_wisata?id={id}"){
        fun createRoute(
            idArtikel: Int?
        ): String {
            return "detail_screen_wisata?id=${idArtikel}"
        }
    }

    data object AddArtikel: Screen("add_artikel")

    data object UpdateArtikel: Screen("update_artikel?img_url={img_url}&nama_wisata={nama_wisata}&lokasi={lokasi}&deskripsi={deskripsi}"){
        fun createRoute(
            img_url: String?,
            nama_wisata: String?,
            lokasi: String?,
            deskripsi: String?
        ): String{
            return "update_artikel?img_url=$img_url&nama_wisata=$nama_wisata&lokasi=$lokasi&deskripsi=$deskripsi"
        }
    }

}