package com.abhranilnxt.kokorolist.di

import com.abhranilnxt.kokorolist.BuildConfig
import com.abhranilnxt.kokorolist.data.remote.AnimeApi
import com.abhranilnxt.kokorolist.data.remote.BackendApi
import com.abhranilnxt.kokorolist.data.repo.AnimeRepository
import com.abhranilnxt.kokorolist.data.utils.Constants
import com.google.firebase.auth.FirebaseAuth
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

    @Singleton
    @Provides
    fun provideBackendApi(): BackendApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackendApi::class.java)
    }

    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}