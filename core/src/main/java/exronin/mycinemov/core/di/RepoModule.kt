package exronin.mycinemov.core.di

import exronin.mycinemov.core.data.MovRepository
import exronin.mycinemov.core.domain.repository.IMovRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindRepository(movieRepository: MovRepository): IMovRepository

}
