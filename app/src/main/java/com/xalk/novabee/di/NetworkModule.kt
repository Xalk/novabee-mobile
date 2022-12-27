package com.xalk.novabee.di

import com.xalk.novabee.api.*
import com.xalk.novabee.utils.Constants.BASE_URL
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
            .baseUrl(BASE_URL)

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

    @Singleton
    @Provides
    fun providesBeehiveAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): BeehiveAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(BeehiveAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesQueenAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): QueenAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(QueenAPI::class.java)
    }

}