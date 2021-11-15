package com.toybethsystems.dokto.di.module

import com.toybethsystems.dokto.data.main.MainRepository
import com.toybethsystems.dokto.data.main.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMainRepository(repo: MainRepositoryImpl): MainRepository

}