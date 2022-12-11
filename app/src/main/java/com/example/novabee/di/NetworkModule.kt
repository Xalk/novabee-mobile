package com.example.novabee.di

import com.example.novabee.api.ApiaryAPI
import com.example.novabee.api.AuthInterceptor
import com.example.novabee.api.UserAPI
import com.example.novabee.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.0.102:5000")

    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI {
        return retrofitBuilder.build().create(UserAPI::class.java)
    }


    @Singleton
    @Provides
    fun providesApiaryAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): ApiaryAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(ApiaryAPI::class.java)
    }

}