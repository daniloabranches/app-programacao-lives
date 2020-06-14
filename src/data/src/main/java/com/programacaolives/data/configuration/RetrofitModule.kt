package com.programacaolives.data.configuration

import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitModule {
    companion object {
        fun get(baseUrl: String): Retrofit {
            val gson = GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }
    }
}