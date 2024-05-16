package exronin.mycinemov.di

import exronin.mycinemov.core.domain.usecase.MovUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Entry point untuk menyediakan dependensi MovieUseCase
 * yang akan digunakan oleh modul Favorite.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    /**
     * Menyediakan instance dari MovieUseCase.
     *
     * @return MovieUseCase instance yang akan diinjeksikan.
     */
    fun movieUseCase(): MovUseCase
}
