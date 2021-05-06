package com.budget.budget.module

import com.budget.budget.state.HomeState
import com.budget.budget.state.TransactionState
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