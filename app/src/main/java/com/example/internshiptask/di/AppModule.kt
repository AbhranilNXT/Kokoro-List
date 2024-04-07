package com.example.internshiptask.di

import com.example.internshiptask.data.remote.AnimeApi
import com.example.internshiptask.data.repo.AnimeRepository
import com.example.internshiptask.data.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent:: class)
object AppModule {

    @Singleton
    @Provides
    fun provideAnimeRepository(api: AnimeApi) = AnimeRepository(api)


    @Singleton
    @Provides
    fun provideAnimeApi(): AnimeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimeApi::class.java)
    }
}