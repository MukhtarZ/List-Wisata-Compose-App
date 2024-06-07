package com.mukhtarz.listwisata.ui

import android.app.Application
import com.mukhtarz.listwisata.data.di.AppModule
import com.mukhtarz.listwisata.data.di.AppmoduleImplement

class WisataListApplication: Application() {
    companion object {
        lateinit var appmodule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appmodule = AppmoduleImplement()
    }
}