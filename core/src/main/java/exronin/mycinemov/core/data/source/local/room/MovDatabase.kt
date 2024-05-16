package exronin.mycinemov.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import exronin.mycinemov.core.data.source.local.entity.MovEntity

@Database(entities = [MovEntity::class], version = 1, exportSchema = false)
abstract class MovDatabase : RoomDatabase() {

    abstract fun movieDao(): MovDao

}