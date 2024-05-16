package exronin.mycinemov.di

import exronin.mycinemov.core.domain.usecase.MovInteractor
import exronin.mycinemov.core.domain.usecase.MovUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger Hilt module untuk menyediakan implementasi dependensi
 * yang digunakan di seluruh aplikasi.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    /**
     * Menyediakan implementasi MovieUseCase menggunakan MovieInteractor.
     *
     * @param movieInteractor Implementasi MovieInteractor yang akan diinjeksikan.
     * @return MovieUseCase yang sudah diimplementasikan.
     */
    @Binds
    abstract fun bindMovieUseCase(movieInteractor: MovInteractor): MovUseCase
}
