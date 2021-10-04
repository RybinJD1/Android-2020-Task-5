package org.rsschool.cats.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.rsschool.cats.repository.RemoteRepository
import org.rsschool.cats.repository.Repository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteService(retrofit: Retrofit): CatApi =
        retrofit.create(CatApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(remoteRepository: RemoteRepository): Repository = remoteRepository
}
