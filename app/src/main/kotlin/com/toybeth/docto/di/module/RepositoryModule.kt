package com.toybeth.docto.di.module

import com.toybeth.docto.data.main.MainRepository
import com.toybeth.docto.data.main.MainRepositoryImpl
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