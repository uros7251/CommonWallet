package com.example.commonwallet.di

import android.content.Context
import androidx.room.Room
import com.example.commonwallet.database.AppDatabase
import com.example.commonwallet.deserializers.ZonedDateTimeDeserializer
import com.example.commonwallet.network.CommonWalletApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.ZonedDateTime
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourcesModule {
    @Provides
    fun provideCommonWalletApiService(): CommonWalletApiService {
        val gson = GsonBuilder()
            .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeDeserializer())
            .create()

        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url()

            val url = originalUrl.newBuilder()
                .addQueryParameter("code", Secrets.CODE)
                .addQueryParameter("clientId", "default")
                .build()

            val requestBuilder = originalRequest.newBuilder().url(url)
            val request = requestBuilder.build()

            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            //.baseUrl("http://10.0.2.2:7065/api/")
            .baseUrl(Secrets.URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(CommonWalletApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "common-wallet"
        ).build()
    }
}