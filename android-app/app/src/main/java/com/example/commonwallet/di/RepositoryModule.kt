package com.example.commonwallet.di

import com.example.commonwallet.data.repository.WalletRepository
import com.example.commonwallet.data.repository.WalletRepositoryImpl
import com.example.commonwallet.network.CommonWalletApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindWalletRepository(walletRepository: WalletRepositoryImpl) : WalletRepository
}