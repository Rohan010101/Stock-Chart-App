package com.example.stockchart.di

import android.content.Context
import com.example.stockchart.data.repository.CompanyRepositoryImpl
import com.example.stockchart.domain.repository.CompanyRepository
import com.example.stockchart.domain.usecase.GetCompaniesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideCompanyRepository(@ApplicationContext context: Context): CompanyRepository {
        return CompanyRepositoryImpl(context)
    }

    @Provides
    fun provideGetCompaniesUseCase(repository: CompanyRepository): GetCompaniesUseCase {
        return GetCompaniesUseCase(repository)
    }
}