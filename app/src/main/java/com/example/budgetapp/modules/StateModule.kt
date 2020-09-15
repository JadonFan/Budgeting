package com.example.budgetapp.modules

import com.example.budgetapp.home.HomeState
import com.example.budgetapp.transactions.TransactionState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StateModule {
    @Singleton
    @Provides
    fun provideHomeState() = HomeState()

    @Singleton
    @Provides
    fun provideTransactionState() = TransactionState()
}