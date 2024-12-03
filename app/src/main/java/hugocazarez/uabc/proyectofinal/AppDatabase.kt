package hugocazarez.uabc.proyectofinal

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteShow::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteShowDao(): FavoriteShowDao
}
