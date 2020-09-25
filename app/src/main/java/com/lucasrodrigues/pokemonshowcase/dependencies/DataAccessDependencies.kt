package com.lucasrodrigues.pokemonshowcase.dependencies

import com.lucasrodrigues.pokemonshowcase.BuildConfig
import com.lucasrodrigues.pokemonshowcase.data_access.local.LocalDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataAccessDependencies {
    val module = module(override = true) {
        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.POKEMON_INFO_BASE_URL)
                .client(
                    OkHttpClient()
                        .newBuilder()
                        .readTimeout(2, TimeUnit.MINUTES)
                        .connectTimeout(2, TimeUnit.MINUTES)
                        .writeTimeout(2, TimeUnit.MINUTES)
                        .callTimeout(2, TimeUnit.MINUTES)
                        .apply {
                            if (BuildConfig.DEBUG) {
                                addInterceptor(
                                    HttpLoggingInterceptor().apply {
                                        level = HttpLoggingInterceptor.Level.BODY
                                    }
                                )
                            }
                        }
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single { LocalDatabase.getInstance(androidContext().applicationContext) }

        single { get<LocalDatabase>().pokemonDao() }
        single { get<LocalDatabase>().remoteKeyDao() }
        single { get<LocalDatabase>().abilityDao() }
        single { get<LocalDatabase>().moveDao() }
        single { get<LocalDatabase>().typeDao() }
    }
}