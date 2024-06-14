package com.mukhtarz.listwisata

import com.chibatching.kotpref.KotprefModel

object KotprefStorage: KotprefModel() {
    var username: String by stringPref()
    var email: String by stringPref()
    var accessToken: String by stringPref()
    var userUid: String by stringPref()
}