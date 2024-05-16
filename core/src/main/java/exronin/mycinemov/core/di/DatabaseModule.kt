package exronin.mycinemov.core.di


import android.content.Context
import androidx.room.Room
import exronin.mycinemov.core.data.source.local.room.MovDao
import exronin.mycinemov.core.data.source.local.room.MovDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovDatabase {
        return Room.databaseBuilder(
            context,
            MovDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(database: MovDatabase): MovDao = database.movieDao()
}
