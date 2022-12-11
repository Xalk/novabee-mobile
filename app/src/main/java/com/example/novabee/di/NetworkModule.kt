package com.example.novabee.di

import com.example.novabee.api.UserAPI
import com.example.novabee.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.0.102:5000")
            .build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofit: Retrofit): UserAPI{
        return retrofit.create(UserAPI::class.java)
    }

}