package com.mukhtarz.listwisata.data.di

import com.mukhtarz.listwisata.KotprefStorage
import com.mukhtarz.listwisata.data.remote.model.APIInterface
import com.mukhtarz.listwisata.data.repository.ListWisataRepository
import com.mukhtarz.listwisata.data.repository.ListWisataRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppModule {
    val getAPI:APIInterface
    val getRepository: ListWisataRepository
}

class AppmoduleImplement: AppModule {


    override val getAPI: APIInterface by lazy {

        val client = OkHttpClient.Builder().addInterceptor(OkkhtpInterceptor())

        Retrofit.Builder()
            .baseUrl("https://wyylgdapprnhmhtiahqi.supabase.co")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(APIInterface::class.java)
    }

    override val getRepository: ListWisataRepository by lazy {
        ListWisataRepositoryImpl(getAPI)
    }

}

class OkkhtpInterceptor: Interceptor {
    val bearerToken = if (KotprefStorage.accessToken == "") "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind5eWxnZGFwcHJuaG1odGlhaHFpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTcwNDA5OTUsImV4cCI6MjAzMjYxNjk5NX0.pAen5VtTDTbnffpTENpIHWzvXJIHxxV4YsCH821ZkRM" else KotprefStorage.accessToken
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("apikey","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind5eWxnZGFwcHJuaG1odGlhaHFpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTcwNDA5OTUsImV4cCI6MjAzMjYxNjk5NX0.pAen5VtTDTbnffpTENpIHWzvXJIHxxV4YsCH821ZkRM")
            .header("Authorization","Bearer $bearerToken")
            .build()
        return chain.proceed(request = request)
    }
}