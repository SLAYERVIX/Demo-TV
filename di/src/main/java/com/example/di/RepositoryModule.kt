package com.example.di

import com.example.data.remote.MoviesApiService
import com.example.data.repo.NetworkRepoImpl
import com.example.domain.repo.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideNetworkRepoInstance(apiService: MoviesApiService) : NetworkRepository {
        return NetworkRepoImpl(apiService)
    }
}