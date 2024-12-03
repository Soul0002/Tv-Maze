package hugocazarez.uabc.proyectofinal

import androidx.room.*

@Dao
interface FavoriteShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg shows: FavoriteShow) // Inserta múltiples favoritos

    @Delete
    fun delete(show: FavoriteShow) // Elimina un favorito

    @Query("SELECT * FROM favorite_shows")
    fun getAll(): List<FavoriteShow> // Obtiene todos los favoritos
}