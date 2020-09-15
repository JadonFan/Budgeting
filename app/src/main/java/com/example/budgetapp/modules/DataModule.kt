package com.example.budgetapp.modules

import android.content.Context
import androidx.room.Room
import com.example.budgetapp.database.AppDatabase
import com.example.budgetapp.home.HomeRepository
import com.example.budgetapp.transactions.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "spending-record.db").build()

    @Singleton
    @Provides
    fun provideSpendingDao(db: AppDatabase) = db.spendingDao()

    @Singleton
    @Provides
    fun provideHomeRepository(db: AppDatabase) = HomeRepository(db)

    @Singleton
    @Provides
    fun provideTransactionRepository(db: AppDatabase) = TransactionRepository(db)
}