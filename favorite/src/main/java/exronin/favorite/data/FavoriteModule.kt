package exronin.favorite.data

import android.content.Context
import exronin.mycinemov.di.FavoriteModuleDependencies
import exronin.favorite.ui.FavoriteMovieFragment
import dagger.BindsInstance
import dagger.Component


@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteModule {
    fun inject(fragment: FavoriteMovieFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun appDependencies(dependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteModule
    }
}
